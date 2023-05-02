package inno.jago.semantic.analyzer.statement

import inno.jago.ast.model.expression.ExpressionNode
import inno.jago.ast.model.expression.unary_expression.IndexExpressionNode
import inno.jago.ast.model.expression.unary_expression.UnaryExpressionNode
import inno.jago.ast.model.expression.unary_expression.UnaryOperators
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.OperandNameNode
import inno.jago.ast.model.statement.AddOpSimpleAssignOperatorNode
import inno.jago.ast.model.statement.AssignmentNode
import inno.jago.ast.model.statement.MulOpSimpleAssignOperatorNode
import inno.jago.ast.model.statement.SimpleAssignOperatorNode
import inno.jago.common.WrongNumberOfExpressionsException
import inno.jago.semantic.IsNotAssignableExpression
import inno.jago.semantic.WrongTypeException
import inno.jago.semantic.analyzer.expression.toSemanticEntity
import inno.jago.semantic.model.ConstEntity
import inno.jago.semantic.model.ScopeNode
import inno.jago.semantic.model.SemanticEntity
import inno.jago.semantic.model.StatementEntity

fun AssignmentNode.toSemanticEntity(scope: ScopeNode): StatementEntity {
    if (leftExpressions.size != rightExpressions.size) {
        throw WrongNumberOfExpressionsException(leftExpressions.size, rightExpressions.size, pos)
    }

    when(assignOperator) {
        is SimpleAssignOperatorNode -> {
            leftExpressions.forEachIndexed { idx, leftExpression ->
                val rightExpression = rightExpressions[idx]

                val leftSemanticEntity = leftExpression.toSemanticEntity(scope)
                val rightSemanticEntity = rightExpression.toSemanticEntity(scope)

                if (!canBeReassigned(leftExpression, leftSemanticEntity, scope)) {
                    throw IsNotAssignableExpression(leftExpression)
                }

                if (leftSemanticEntity.type != rightSemanticEntity.type) {
                    throw WrongTypeException(leftSemanticEntity.type, actualType = rightSemanticEntity.type, pos = pos)
                }
            }
        }
        is AddOpSimpleAssignOperatorNode, is MulOpSimpleAssignOperatorNode -> {
            if (leftExpressions.size != 1) {
                throw WrongNumberOfExpressionsException(1, leftExpressions.size, pos)
            }
            val leftSemanticEntity = leftExpressions[0].toSemanticEntity(scope)
            val rightSemanticEntity = rightExpressions[0].toSemanticEntity(scope)

            if (!canBeReassigned(leftExpressions[0], leftSemanticEntity, scope)) {
                throw IsNotAssignableExpression(leftExpressions[0])
            }

            if (leftSemanticEntity.type != rightSemanticEntity.type) {
                throw WrongTypeException(leftSemanticEntity.type, actualType = rightSemanticEntity.type, pos = pos)
            }
        }
    }

    return StatementEntity()
}

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
        else -> return false
    }
}
