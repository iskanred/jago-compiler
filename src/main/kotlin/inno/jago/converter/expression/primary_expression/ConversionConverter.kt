package inno.jago.converter.expression.primary_expression

import inno.jago.ast.expression.unary_expression.ConversionNode
import inno.jago.converter.common.toPos
import inno.jago.converter.expression.toExpressionNode
import inno.jago.converter.type.toTypeNode

fun GoParser.ConversionContext.toConversionNode(): ConversionNode {
    return ConversionNode(
        pos = toPos(),
        type = type().toTypeNode(),
        expression = expression().toExpressionNode()
    )
}
