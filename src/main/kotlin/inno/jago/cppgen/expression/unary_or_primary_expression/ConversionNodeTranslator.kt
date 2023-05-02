package inno.jago.cppgen.expression.unary_or_primary_expression

import inno.jago.ast.model.expression.unary_expression.ConversionNode
import inno.jago.cppgen.expression.translateToCode
import inno.jago.cppgen.type.translateToCode

fun ConversionNode.translateToCode(): String {
    var conversionInstruction = this.type.translateToCode() + "("
    conversionInstruction += this.expression.translateToCode()
    conversionInstruction += ")"
    return  conversionInstruction
}
