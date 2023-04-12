package inno.jago.converter.type

import GoParser
import inno.jago.exception.EntityNotSupported
import inno.jago.exception.UnreachableCodeException
import inno.jago.exception.UnknownTypeException
import inno.jago.ast.type.ArrayTypeNode
import inno.jago.ast.type.BoolTypeNode
import inno.jago.ast.type.DoubleTypeNode
import inno.jago.ast.type.FunctionTypeNode
import inno.jago.ast.type.IntegerTypeNode
import inno.jago.ast.type.PointerTypeNode
import inno.jago.ast.type.StringTypeNode
import inno.jago.ast.type.TypeNode
import inno.jago.common.BOOL_TYPE_NAME
import inno.jago.common.DOUBLE_TYPE_NAME
import inno.jago.common.INT_TYPE_NAME
import inno.jago.common.STRING_TYPE_NAME
import inno.jago.converter.common.toPos
import inno.jago.converter.expression.toExpressionNode
import inno.jago.converter.signature.toSignatureNode
import inno.jago.lexer.Pos

fun GoParser.TypeContext.toTypeNode(): TypeNode =
    typeName()?.IDENTIFIER()?.text.toTypeNode(pos = toPos())
        ?: typeLit().toTypeNode()
        ?: throw UnknownTypeException(entityName = text, pos = toPos())

fun String?.toTypeNode(pos: Pos): TypeNode? = when {
    this == INT_TYPE_NAME -> IntegerTypeNode(pos = pos)
    this == DOUBLE_TYPE_NAME -> DoubleTypeNode(pos = pos)
    this == STRING_TYPE_NAME -> StringTypeNode(pos = pos)
    this == BOOL_TYPE_NAME -> BoolTypeNode(pos = pos)
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
