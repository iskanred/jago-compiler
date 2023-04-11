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

    return TODO()
}

fun String?.toTypeNode(pos: Pos): TypeNode? = when {
    this == IntegerTypeNode.typeName -> IntegerTypeNode(pos = pos)
    this == DoubleTypeNode.typeName -> DoubleTypeNode(pos = pos)
    this == StringTypeNode.typeName -> StringTypeNode(pos = pos)
    else -> null
}

fun GoParser.TypeLitContext?.toTypeNode(): TypeNode? = this?.let {
    it.arrayType()?.let { arrayTypeContext ->

    }
    it.pointerType()?.let  { pointerTypeContext ->

    }
    it.functionType()?.let { functionTypeContext ->

    }
    TODO()
}

fun GoParser.ArrayTypeContext(): ArrayTypeNodeNode
