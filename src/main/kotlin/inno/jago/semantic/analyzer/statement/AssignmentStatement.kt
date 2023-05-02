package inno.jago.semantic.analyzer.statement

import inno.jago.ast.model.expression.ExpressionNode
import inno.jago.ast.model.expression.unary_expression.IndexExpressionNode
import inno.jago.ast.model.expression.unary_expression.UnaryExpressionNode
import inno.jago.ast.model.expression.unary_expression.UnaryOperators
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.OperandNameNode
import inno.jago.ast.model.statement.AssignmentNode
import inno.jago.common.WrongNumberOfExpressionsException
import inno.jago.semantic.NonAddressableExpression
import inno.jago.semantic.WrongTypeException
import inno.jago.semantic.analyzer.expression.toSemanticEntity
import inno.jago.semantic.model.ScopeNode
import inno.jago.semantic.model.StatementEntity

fun AssignmentNode.toSemanticEntity(scope: ScopeNode): StatementEntity {
    if (leftExpressions.size != rightExpressions.size) {
        throw WrongNumberOfExpressionsException(leftExpressions.size, rightExpressions.size, pos)
    }

    leftExpressions.forEachIndexed { idx, leftExpression ->
        val rightExpression = rightExpressions[idx]

        val leftSemanticEntity = leftExpression.toSemanticEntity(scope)
        val rightSemanticEntity = rightExpression.toSemanticEntity(scope)

        if (!canBeReassigned(leftExpression, scope)) {
            throw NonAddressableExpression(leftExpression)
        }

        if (leftSemanticEntity.type != rightSemanticEntity.type) {
            throw WrongTypeException(leftSemanticEntity.type, actualType = rightSemanticEntity.type, pos = pos)
        }
    }

    return StatementEntity()
}

fun canBeReassigned(expression: ExpressionNode, scope: ScopeNode): Boolean {
    when (expression) {
        is OperandNameNode -> {
            TODO("проверить, что переменная не константа")
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
