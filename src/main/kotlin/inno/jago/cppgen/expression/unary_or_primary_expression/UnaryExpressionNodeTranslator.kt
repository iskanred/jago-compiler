package inno.jago.cppgen.expression.unary_or_primary_expression

import inno.jago.ast.model.expression.unary_expression.UnaryExpressionNode
import inno.jago.ast.model.expression.unary_expression.UnaryOperatorNode
import inno.jago.ast.model.expression.unary_expression.UnaryOperators
import inno.jago.common.EntityNotSupportedException

fun UnaryExpressionNode.translateToCode(): String {
    val expr = unaryOrPrimaryExpression.translateToCode()
    return "${operator?.translateToCode() ?: ""}$expr"
}

fun UnaryOperatorNode.translateToCode(): String = when (this.operator) {
    UnaryOperators.PLUS -> "+"
    UnaryOperators.MINUS -> "-"
    UnaryOperators.EXCLAMATION -> "!"
    UnaryOperators.CARET -> "^"
    UnaryOperators.ASTERISK -> "*"
    UnaryOperators.AMPERSAND -> "&"
    UnaryOperators.RECEIVE -> throw EntityNotSupportedException("RECEIVE")
}
