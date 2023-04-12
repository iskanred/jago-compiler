package inno.jago.converter.expression

import inno.jago.exception.UnreachableCodeException
import inno.jago.exception.WrongBinaryExpressionNumber
import inno.jago.ast.expression.ExpressionNode
import inno.jago.ast.expression.binary_expression.BinaryExpression
import inno.jago.converter.common.toPos

fun GoParser.ExpressionContext.toExpressionNode(): ExpressionNode {
    unaryExpr()?.let {
        return it.toUnaryOrPrimaryExpression()
    }

    binary_op()?.let {
        if (expression().size != 2) {
            throw WrongBinaryExpressionNumber()
        }

        val left = expression()[0]
        val right = expression()[1]

        return BinaryExpression(
            pos = toPos(),
            binaryOperator = it.toBinaryOperator(),
            leftExpression = left.toExpressionNode(),
            rightExpression = right.toExpressionNode()
        )
    }

    throw UnreachableCodeException()
}
