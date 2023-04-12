package inno.jago.converter.expression

import inno.jago.exception.UnreachableCodeException
import inno.jago.ast.expression.unary_expression.UnaryExpressionNode
import inno.jago.ast.expression.unary_expression.UnaryOrPrimaryExpression
import inno.jago.converter.expression.primary_expression.toPrimaryExpressionNode
import inno.jago.converter.common.toPos

fun GoParser.UnaryExprContext.toUnaryOrPrimaryExpression(): UnaryOrPrimaryExpression {
    primaryExpr()?.let {
        return it.toPrimaryExpressionNode()
    }

    unaryExpr()?.let {
        return UnaryExpressionNode(
            pos = toPos(),
            operator = it.unary_op().toUnaryOperator(),
            unaryOrPrimaryExpression = it.toUnaryOrPrimaryExpression()
        )
    }

    throw UnreachableCodeException()
}



