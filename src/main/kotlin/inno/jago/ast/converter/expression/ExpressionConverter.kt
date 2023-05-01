package inno.jago.ast.converter.expression

import inno.jago.ast.WrongBinaryExpressionNumberException
import inno.jago.common.UnreachableCodeException
import inno.jago.ast.model.expression.ExpressionNode
import inno.jago.ast.model.expression.binary_expression.BinaryExpression
import inno.jago.ast.converter.common.toPos

fun GoParser.ExpressionContext.toExpressionNode(): ExpressionNode {
    unaryExpr()?.let {
        return it.toUnaryOrPrimaryExpression()
    }

    binary_op()?.let {
        if (expression().size != 2) {
            throw WrongBinaryExpressionNumberException()
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
