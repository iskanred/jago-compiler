package inno.jago.converter.expression

import inno.jago.exception.UnreachableCodeException
import inno.jago.ast.expression.unary_expression.UnaryExpressionNode
import inno.jago.ast.expression.unary_expression.UnaryOrPrimaryExpression
import inno.jago.converter.expression.primary_expression.toPrimaryExpressionNode
import inno.jago.converter.common.toPos

fun GoParser.UnaryExprContext.toUnaryOrPrimaryExpression(): UnaryOrPrimaryExpression {
    primaryExpr()?.let { primaryExpr ->
        return primaryExpr.toPrimaryExpressionNode()
    }

    unaryExpr()?.let { unaryExpr ->
        return UnaryExpressionNode(
            pos = toPos(),
            operator = unary_op().toUnaryOperator(),
            unaryOrPrimaryExpression = unaryExpr.toUnaryOrPrimaryExpression()
        )
    }

    throw UnreachableCodeException()
}



