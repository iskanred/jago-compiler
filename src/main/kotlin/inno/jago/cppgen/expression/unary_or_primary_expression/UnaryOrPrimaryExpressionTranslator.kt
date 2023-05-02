package inno.jago.cppgen.expression.unary_or_primary_expression

import inno.jago.ast.model.expression.unary_expression.PrimaryExpressionNode
import inno.jago.ast.model.expression.unary_expression.UnaryExpressionNode
import inno.jago.ast.model.expression.unary_expression.UnaryOrPrimaryExpression

fun UnaryOrPrimaryExpression.translateToCode(): String = when (this) {
    is UnaryExpressionNode -> { TODO() }
    is PrimaryExpressionNode -> { TODO()}
}

