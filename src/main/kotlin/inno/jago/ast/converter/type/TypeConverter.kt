package inno.jago.ast.converter.type

import GoParser
import inno.jago.ast.ArrayLengthNotIntegerLiteralException
import inno.jago.ast.UnknownTypeException
import inno.jago.common.UnreachableCodeException
import inno.jago.ast.model.type.ArrayTypeNode
import inno.jago.ast.model.type.BoolTypeNode
import inno.jago.ast.model.type.DoubleTypeNode
import inno.jago.ast.model.type.FunctionTypeNode
import inno.jago.ast.model.type.IntegerTypeNode
import inno.jago.ast.model.type.PointerTypeNode
import inno.jago.ast.model.type.StringTypeNode
import inno.jago.ast.model.type.TypeNode
import inno.jago.common.BOOL_TYPE_NAME
import inno.jago.common.DOUBLE_TYPE_NAME
import inno.jago.common.INT_TYPE_NAME
import inno.jago.common.STRING_TYPE_NAME
import inno.jago.ast.converter.common.toPos
import inno.jago.ast.converter.expression.toExpressionNode
import inno.jago.ast.converter.signature.toSignatureNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.literal_operand.IntegerLiteralNode
import inno.jago.common.EntityNotSupportedException
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
        throw EntityNotSupportedException("Structures")
    }
    typeLitContext.interfaceType()?.let {
        throw EntityNotSupportedException("Interfaces")
    }
    typeLitContext.mapType()?.let {
        throw EntityNotSupportedException("Maps")
    }
    typeLitContext.channelType()?.let {
        throw EntityNotSupportedException("Channels")
    }
    typeLitContext.sliceType()?.let {
        throw EntityNotSupportedException("Slices")
    }
    throw UnreachableCodeException()
}

fun GoParser.ArrayTypeContext.toArrayTypeNode() = ArrayTypeNode(
    pos = toPos(),
    length = arrayLength().expression().toExpressionNode().let {
        (it as? IntegerLiteralNode) ?: throw ArrayLengthNotIntegerLiteralException(expressionNode = it)
    },
    elementType = elementType().type().toTypeNode()
)

fun GoParser.PointerTypeContext.toPointerTypeNode() = PointerTypeNode(
    pos = toPos(),
    baseType = baseType().type().toTypeNode()
)

fun GoParser.FunctionTypeContext.toFunctionTypeNode() = FunctionTypeNode(
    pos = toPos(),
    signature = signature().toSignatureNode()
)
