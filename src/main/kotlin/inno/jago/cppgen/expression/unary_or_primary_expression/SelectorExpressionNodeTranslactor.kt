package inno.jago.cppgen.expression.unary_or_primary_expression

import inno.jago.ast.model.expression.unary_expression.SelectorExpressionNode

fun SelectorExpressionNode.translateToCode(): String {
    return "${primaryExpression.translateToCode()}.$selector"
}
