package inno.jago.cppgen.expression.operand

import inno.jago.ast.model.expression.unary_expression.ApplicationExpressionNode
import inno.jago.ast.model.expression.unary_expression.ConversionNode
import inno.jago.ast.model.expression.unary_expression.IndexExpressionNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.ExpressionOperandNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.LiteralOperandNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.OperandNameNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.OperandNode
import inno.jago.common.UnreachableCodeException

fun OperandNode.translateToCode(): String = when (this) {
    is LiteralOperandNode -> translateToCode()
    is OperandNameNode -> translateToCode()
    is ExpressionOperandNode -> translateToCode()
}

fun LiteralOperandNode.translateToCode(): String {
    TODO()
}

fun OperandNameNode.translateToCode(): String {
    TODO()
}

fun ExpressionOperandNode.translateToCode(): String {
    TODO()
}
