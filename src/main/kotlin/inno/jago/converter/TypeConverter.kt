package inno.jago.converter

import GoParser
import inno.jago.ast.type.DoubleTypeNode
import inno.jago.ast.type.IntegerTypeNode
import inno.jago.ast.type.StringTypeNode
import inno.jago.ast.type.TypeNode
import inno.jago.lexer.Pos

fun GoParser.TypeContext.toTypeNode(): TypeNode {
    typeName()?.IDENTIFIER()?.text.toTypeNode(pos = toPos())
        ?: typeLit()

    return TypeNode(
        pos = toPos(),
        name = typeName().IDENTIFIER().text
    )
}

fun String?.toTypeNode(pos: Pos): TypeNode? = when {
    this == IntegerTypeNode.typeName -> IntegerTypeNode(pos = pos)
    this == DoubleTypeNode.typeName -> DoubleTypeNode(pos = pos)
    this == StringTypeNode.typeName -> StringTypeNode(pos = pos)
    else -> null
}

fun GoParser.TypeLitContext?.toTypeNode(pos: Pos): TypeNode? = when {

    else -> null
}
