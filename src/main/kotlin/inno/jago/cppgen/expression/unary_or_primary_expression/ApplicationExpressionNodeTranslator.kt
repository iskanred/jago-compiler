package inno.jago.cppgen.expression.unary_or_primary_expression

import inno.jago.ast.model.expression.unary_expression.ApplicationExpressionNode
import inno.jago.cppgen.expression.translateToCode
import java.lang.StringBuilder

fun ApplicationExpressionNode.translateToCode(): String {
    val funcName = leftExpression.translateToCode()
    val args = StringBuilder()
    expressions.forEachIndexed { index, expressionNode ->
        args.append(expressionNode.translateToCode())
        if (index != expressions.size - 1) {
            args.append(", ")
        }
    }
    return if (funcName == "print") {
        "cout << " + expressions.map { it.translateToCode() }.joinToString(separator = " << ") { it }
    } else {
        "${leftExpression.translateToCode()}($args)"
    }
}
