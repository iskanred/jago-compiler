package inno.jago.semantic.analyzer.statement

import inno.jago.ast.model.expression.ExpressionNode
import inno.jago.ast.model.expression.unary_expression.IndexExpressionNode
import inno.jago.ast.model.expression.unary_expression.SelectorExpressionNode
import inno.jago.ast.model.expression.unary_expression.UnaryExpressionNode
import inno.jago.ast.model.expression.unary_expression.UnaryOperators
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.OperandNameNode
import inno.jago.ast.model.statement.AddOpSimpleAssignOperatorNode
import inno.jago.ast.model.statement.AssignmentNode
import inno.jago.ast.model.statement.MulOpSimpleAssignOperatorNode
import inno.jago.ast.model.statement.SimpleAssignOperatorNode
import inno.jago.common.JaGoException
import inno.jago.common.WrongNumberOfExpressionsException
import inno.jago.semantic.IsNotAssignableExpression
import inno.jago.semantic.WrongTypeException
import inno.jago.semantic.analyzer.expression.toSemanticEntity
import inno.jago.semantic.model.ConstEntity
import inno.jago.semantic.model.ScopeNode
import inno.jago.semantic.model.SemanticEntity
import inno.jago.semantic.model.StatementEntity
import inno.jago.semantic.model.Type

@Suppress("ThrowsCount")
fun AssignmentNode.toSemanticEntity(scope: ScopeNode): StatementEntity {
    val semanticEntities = rightExpressions.map { it.toSemanticEntity(scope) }

    if (semanticEntities.any { it.type is Type.TupleType }) {
        if (semanticEntities.size != 1) {
            throw JaGoException(msg = "Can be only one tuple in expressions at $pos")
        }

        if (assignOperator !is SimpleAssignOperatorNode) {
            throw JaGoException(msg = "Can use only assignment operator for multiple variables at $pos")
        }

        val tupleElements = (semanticEntities.first().type as Type.TupleType).elementTypes
        if (leftExpressions.size != tupleElements.size) {
            throw WrongNumberOfExpressionsException(
                expected = leftExpressions.size,
                actual = tupleElements.size,
                pos = pos
            )
        }

        leftExpressions
            .map { it.toSemanticEntity(scope) }
            .zip(tupleElements)
            .forEach { (lhs, rhsType) ->
                if (lhs.type != rhsType) {
                    throw WrongTypeException(lhs.type, actualType = rhsType, pos = pos)
                }
            }
    } else {
        if (leftExpressions.size != rightExpressions.size) {
            throw WrongNumberOfExpressionsException(
                expected = leftExpressions.size,
                actual = rightExpressions.size,
                pos = pos
            )
        }

        when (assignOperator) {
            is SimpleAssignOperatorNode -> {
                leftExpressions.forEachIndexed { idx, leftExpression ->
                    val rightExpression = rightExpressions[idx]

                    val leftSemanticEntity = leftExpression.toSemanticEntity(scope)
                    val rightSemanticEntity = rightExpression.toSemanticEntity(scope)

                    if (!canBeReassigned(leftExpression, leftSemanticEntity, scope)) {
                        throw IsNotAssignableExpression(leftExpression)
                    }

                    if (leftSemanticEntity.type != rightSemanticEntity.type) {
                        throw WrongTypeException(
                            leftSemanticEntity.type,
                            actualType = rightSemanticEntity.type,
                            pos = pos
                        )
                    }
                }
            }

            is AddOpSimpleAssignOperatorNode, is MulOpSimpleAssignOperatorNode -> {
                if (leftExpressions.size != 1) {
                    throw WrongNumberOfExpressionsException(expected = 1, actual = leftExpressions.size, pos = pos)
                }
                val leftSemanticEntity = leftExpressions.first().toSemanticEntity(scope)
                val rightSemanticEntity = rightExpressions.first().toSemanticEntity(scope)

                if (!canBeReassigned(leftExpressions.first(), leftSemanticEntity, scope)) {
                    throw IsNotAssignableExpression(leftExpressions.first())
                }

                if (leftSemanticEntity.type != rightSemanticEntity.type) {
                    throw WrongTypeException(leftSemanticEntity.type, actualType = rightSemanticEntity.type, pos = pos)
                }
            }
        }
    }

    return StatementEntity()
}

@Suppress("ReturnCount")
fun canBeReassigned(expression: ExpressionNode, semanticEntity: SemanticEntity, scope: ScopeNode): Boolean {
    when (expression) {
        is OperandNameNode -> {
            return semanticEntity !is ConstEntity
        }
        is UnaryExpressionNode -> {
            expression.operator?.let {
                if (expression.operator.operator == UnaryOperators.ASTERISK) {
                    return true
                }
            }
            return false
        }
        is IndexExpressionNode -> {
            expression.toSemanticEntity(scope)
            return true
        }
        is SelectorExpressionNode -> {
            return true
        }
        else -> return false
    }
}
