package inno.jago.cppgen.expression.unary_or_primary_expression

import inno.jago.ast.model.expression.unary_expression.ApplicationExpressionNode
import inno.jago.common.BOOL_TYPE_NAME
import inno.jago.common.DOUBLE_TYPE_NAME
import inno.jago.common.INT_TYPE_NAME
import inno.jago.common.STRING_TYPE_NAME
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
    return when(funcName) {
        "print" -> expressions.joinToString(prefix = "cout << ", separator = " << ", postfix = " << endl") { it.translateToCode() }
        "${INT_TYPE_NAME}_to_$STRING_TYPE_NAME",
        "${DOUBLE_TYPE_NAME}_to_$STRING_TYPE_NAME",
        "${BOOL_TYPE_NAME}_to_$STRING_TYPE_NAME" -> "std::to_string(${expressions.first().translateToCode()})"
        else -> "${leftExpression.translateToCode()}($args)"
    }
}
