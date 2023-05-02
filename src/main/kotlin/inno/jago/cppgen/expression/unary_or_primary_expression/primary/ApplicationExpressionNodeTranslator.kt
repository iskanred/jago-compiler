package inno.jago.cppgen.expression.unary_or_primary_expression.primary

import inno.jago.ast.model.expression.unary_expression.ApplicationExpressionNode
import inno.jago.cppgen.expression.translateToCode
import inno.jago.cppgen.expression.unary_or_primary_expression.translateToCode
import java.lang.StringBuilder

fun ApplicationExpressionNode.translateToCode(): String {
    val args = StringBuilder()
    expressions.forEachIndexed { index, expressionNode ->
        args.append(expressionNode.translateToCode())
        if (index != expressions.size - 1) {
            args.append(", ")
        }
    }
    return "${leftExpression.translateToCode()}($args)"
}
