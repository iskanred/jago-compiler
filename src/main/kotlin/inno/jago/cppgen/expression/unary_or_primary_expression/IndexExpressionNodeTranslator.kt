package inno.jago.cppgen.expression.unary_or_primary_expression

import inno.jago.ast.model.expression.unary_expression.IndexExpressionNode
import inno.jago.cppgen.expression.translateToCode

fun IndexExpressionNode.translateToCode(): String {
    var indexExpressionInstruction = ""
    indexExpressionInstruction = this.primaryExpression.translateToCode()
    indexExpressionInstruction += "[" + this.expression.translateToCode() + "]"
    return indexExpressionInstruction
}
