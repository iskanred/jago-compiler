package inno.jago.cppgen.expression

import inno.jago.ast.model.expression.ExpressionNode
import inno.jago.ast.model.expression.binary_expression.BinaryExpression
import inno.jago.ast.model.expression.unary_expression.UnaryOrPrimaryExpressionNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.literal_operand.LiteralNode
import inno.jago.common.UnreachableCodeException
import inno.jago.cppgen.expression.binary_expression.translateToCode
import inno.jago.cppgen.expression.operand.translateToCode
import inno.jago.cppgen.expression.unary_or_primary_expression.translateToCode

fun ExpressionNode.translateToCode(): String = when(this) {
    is UnaryOrPrimaryExpressionNode -> translateToCode()
    is BinaryExpression -> translateToCode()
    is LiteralNode -> translateToCode()
    else -> throw UnreachableCodeException()
}
