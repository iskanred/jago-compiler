package inno.jago.converter.type

import GoParser
import inno.jago.EntityNotSupported
import inno.jago.UnreachableCodeException
import inno.jago.WrongEntityException
import inno.jago.ast.type.ArrayTypeNode
import inno.jago.ast.type.BoolTypeNode
import inno.jago.ast.type.DoubleTypeNode
import inno.jago.ast.type.FunctionTypeNode
import inno.jago.ast.type.IntegerTypeNode
import inno.jago.ast.type.PointerTypeNode
import inno.jago.ast.type.StringTypeNode
import inno.jago.ast.type.TypeNode
import inno.jago.converter.common.toPos
import inno.jago.converter.expression.toExpressionNode
import inno.jago.converter.signature.toSignatureNode
import inno.jago.lexer.Pos

fun GoParser.TypeContext.toTypeNode(): TypeNode =
    typeName()?.IDENTIFIER()?.text.toTypeNode(pos = toPos())
        ?: typeLit().toTypeNode()
        ?: throw WrongEntityException(entityName = text, pos = toPos())

fun String?.toTypeNode(pos: Pos): TypeNode? = when {
    this == IntegerTypeNode.typeName -> IntegerTypeNode(pos = pos)
    this == DoubleTypeNode.typeName -> DoubleTypeNode(pos = pos)
    this == StringTypeNode.typeName -> StringTypeNode(pos = pos)
    this == BoolTypeNode.typeName -> BoolTypeNode(pos = pos)
    else -> null
}

fun GoParser.TypeLitContext?.toTypeNode(): TypeNode? = this?.let { typeLitContext ->
    typeLitContext.arrayType()?.let { arrayTypeContext ->
        return arrayTypeContext.toArrayTypeNode()
    }
    typeLitContext.pointerType()?.let  { pointerTypeContext ->
        return pointerTypeContext.toPointerTypeNode()
    }
    typeLitContext.functionType()?.let { functionTypeContext ->
        return functionTypeContext.toFunctionTypeNode()
    }
    typeLitContext.structType()?.let {
        throw EntityNotSupported("Structures")
    }
    typeLitContext.interfaceType()?.let {
        throw EntityNotSupported("Interfaces")
    }
    typeLitContext.mapType()?.let {
        throw EntityNotSupported("Maps")
    }
    typeLitContext.channelType()?.let {
        throw EntityNotSupported("Channels")
    }
    typeLitContext.sliceType()?.let {
        throw EntityNotSupported("Slices")
    }
    throw UnreachableCodeException()
}

fun GoParser.ArrayTypeContext.toArrayTypeNode() = ArrayTypeNode(
    pos = toPos(),
    length = arrayLength().expression().toExpressionNode(),
    elementType = elementType().type().toTypeNode()
)

fun GoParser.PointerTypeContext.toPointerTypeNode() = PointerTypeNode(
    pos = toPos(),
    baseType = baseType().type().toTypeNode()
)

fun GoParser.FunctionTypeContext.toFunctionTypeNode() = FunctionTypeNode(
    pos = toPos(),
    elementType = signature().toSignatureNode()
)
