package inno.jago.ast.converter.expression.primary_expression

import inno.jago.ast.model.expression.unary_expression.ConversionNode
import inno.jago.ast.converter.common.toPos
import inno.jago.ast.converter.expression.toExpressionNode
import inno.jago.ast.converter.type.toTypeNode

fun GoParser.ConversionContext.toConversionNode(): ConversionNode {
    return ConversionNode(
        pos = toPos(),
        type = type().toTypeNode(),
        expression = expression().toExpressionNode()
    )
}
