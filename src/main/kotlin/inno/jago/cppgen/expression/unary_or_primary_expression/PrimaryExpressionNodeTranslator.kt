package inno.jago.cppgen.expression.unary_or_primary_expression

import inno.jago.ast.model.expression.unary_expression.ApplicationExpressionNode
import inno.jago.ast.model.expression.unary_expression.ConversionNode
import inno.jago.ast.model.expression.unary_expression.IndexExpressionNode
import inno.jago.ast.model.expression.unary_expression.PrimaryExpressionNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.ExpressionOperandNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.LiteralOperandNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.OperandNameNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.OperandNode
import inno.jago.common.UnreachableCodeException
import inno.jago.cppgen.expression.operand.translateToCode
import inno.jago.cppgen.expression.translateToCode


fun PrimaryExpressionNode.translateToCode(): String = when (this) {
    is ApplicationExpressionNode -> translateToCode()
    is ConversionNode -> TODO()
    is ExpressionOperandNode -> TODO()
    is IndexExpressionNode -> TODO()
    is OperandNode -> this.translateToCode()
    else -> throw UnreachableCodeException()
}


