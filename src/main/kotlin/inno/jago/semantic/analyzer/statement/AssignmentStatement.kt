package inno.jago.semantic.analyzer.statement

import inno.jago.ast.model.expression.ExpressionNode
import inno.jago.ast.model.expression.unary_expression.IndexExpressionNode
import inno.jago.ast.model.expression.unary_expression.UnaryExpressionNode
import inno.jago.ast.model.expression.unary_expression.UnaryOperators
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.OperandNameNode
import inno.jago.ast.model.statement.AssignmentNode
import inno.jago.common.WrongNumberOfExpressionsException
import inno.jago.semantic.analyzer.expression.toSemanticEntity
import inno.jago.semantic.model.EntityType
import inno.jago.semantic.model.ScopeNode
import inno.jago.semantic.model.SemanticEntity
import inno.jago.semantic.model.Type

fun AssignmentNode.toSemanticEntity(scope: ScopeNode): SemanticEntity {
    if (leftExpressions.size != rightExpressions.size) {
        throw WrongNumberOfExpressionsException(leftExpressions.size, rightExpressions.size, pos)
    }

    leftExpressions.forEachIndexed { idx, leftExpression ->
        val rightExpression = rightExpressions[idx]

        val leftSemanticEntity = leftExpression.toSemanticEntity(scope)
        val rightSemanticEntity = rightExpression.toSemanticEntity(scope)

        if (!canBeReassigned(leftExpression, scope)) {
        }

        if (leftSemanticEntity.type != rightSemanticEntity.type) {

        }
    }

    return SemanticEntity(
        type = Type.UnitType,
        pos = pos,
        entityType = EntityType.NO_IDENTIFIER
    )
}

fun canBeReassigned(expression: ExpressionNode, scope: ScopeNode): Boolean {
    when (expression) {
        is OperandNameNode -> {
            // TODO: проверить, что переменная не константа
            return true
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
