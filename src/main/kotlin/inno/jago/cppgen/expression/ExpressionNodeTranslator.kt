package inno.jago.cppgen.expression

import inno.jago.ast.model.expression.ExpressionNode
import inno.jago.ast.model.expression.binary_expression.BinaryExpression
import inno.jago.ast.model.expression.unary_expression.UnaryOrPrimaryExpressionNode
import inno.jago.common.UnreachableCodeException
import inno.jago.cppgen.expression.binary_expression.translateToCode

fun ExpressionNode.translateToCode(): String = when(this) {
    is UnaryOrPrimaryExpressionNode -> translateToCode()
    is BinaryExpression -> translateToCode()
    else -> throw UnreachableCodeException()
}
