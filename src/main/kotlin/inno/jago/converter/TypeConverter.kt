package inno.jago.converter

import GoParser
import inno.jago.EntityNotSupported
import inno.jago.UnreachableCodeException
import inno.jago.ast.expression.ExpressionNode
import inno.jago.ast.type.ArrayTypeNode
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
    it.structType()?.let {
        throw EntityNotSupported("Structures")
    }
    it.interfaceType()?.let {
        throw EntityNotSupported("Interfaces")
    }
    it.mapType()?.let {
        throw EntityNotSupported("Maps")
    }
    it.channelType()?.let {
        throw EntityNotSupported("Channels")
    }
    it.sliceType()?.let {
        throw EntityNotSupported("Slices")
    }
    throw UnreachableCodeException()
}

fun GoParser.ArrayTypeContext.toArrayTypeNode() = ArrayTypeNode(
    pos = toPos(),
    length = arrayLength().expression(),
    elementType = elementType().type().toTypeNode()
)
