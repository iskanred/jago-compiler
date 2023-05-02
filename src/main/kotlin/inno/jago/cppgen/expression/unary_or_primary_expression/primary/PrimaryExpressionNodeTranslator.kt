package inno.jago.cppgen.expression.unary_or_primary_expression.primary

import inno.jago.ast.model.expression.unary_expression.ApplicationExpressionNode
import inno.jago.ast.model.expression.unary_expression.PrimaryExpressionNode


fun PrimaryExpressionNode.translateToCode(): String = when (this) {
    is ApplicationExpressionNode -> TODO()

}


