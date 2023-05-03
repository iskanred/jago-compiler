package inno.jago.cppgen.expression.operand

import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.literal_operand.ElementNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.literal_operand.ExpressionElementNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.literal_operand.LiteralValueElementNode
import inno.jago.cppgen.expression.translateToCode
import java.lang.StringBuilder

fun ElementNode.translateToCode(): String = when (this) {
    is LiteralValueElementNode -> {
        val output = StringBuilder()
        elements.forEach { elementNode ->
            output.append(elementNode.translateToCode())
            output.append(", ")
        }
        output.toString().dropLast(1)
    }
    is ExpressionElementNode -> {
        expression.translateToCode()
    }
}
