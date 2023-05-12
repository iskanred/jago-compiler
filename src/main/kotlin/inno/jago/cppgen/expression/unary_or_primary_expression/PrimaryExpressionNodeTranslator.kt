@file:Suppress("PackageNaming")
package inno.jago.cppgen.expression.unary_or_primary_expression

import inno.jago.ast.model.expression.unary_expression.ApplicationExpressionNode
import inno.jago.ast.model.expression.unary_expression.ConversionNode
import inno.jago.ast.model.expression.unary_expression.IndexExpressionNode
import inno.jago.ast.model.expression.unary_expression.PrimaryExpressionNode
import inno.jago.ast.model.expression.unary_expression.SelectorExpressionNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.OperandNode
import inno.jago.ast.model.type.StructTypeNode
import inno.jago.common.UnreachableCodeException
import inno.jago.cppgen.expression.operand.translateToCode
import inno.jago.cppgen.expression.translateToCode
import inno.jago.cppgen.type.translateToCode
import inno.jago.cppgen.expression.unary_or_primary_expression.translateToCode as translateIndexToCode
import inno.jago.cppgen.expression.unary_or_primary_expression.translateToCode as translateApplicationToCode
import inno.jago.cppgen.expression.unary_or_primary_expression.translateToCode as translatedConversionToCode
import inno.jago.cppgen.expression.unary_or_primary_expression.translateToCode as translateSelectorToCode

fun PrimaryExpressionNode.translateToCode(): String = when (this) {
    is ApplicationExpressionNode -> translateApplicationToCode()
    is ConversionNode -> translatedConversionToCode()
    is IndexExpressionNode -> translateIndexToCode()
    is OperandNode -> translateToCode()
    is SelectorExpressionNode -> translateSelectorToCode()
    else -> throw UnreachableCodeException()
}


