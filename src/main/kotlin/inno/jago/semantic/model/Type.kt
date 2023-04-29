package inno.jago.semantic.model

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

sealed class Type {

    object IntegerType : Type() {
        override fun toString(): String = INT_TYPE_NAME
    }

    object DoubleType : Type() {
        override fun toString(): String = DOUBLE_TYPE_NAME
    }

    object StringType : Type() {
        override fun toString(): String = STRING_TYPE_NAME
    }

    object BoolType : Type() {
        override fun toString(): String = BOOL_TYPE_NAME
    }

    object ImportType : Type()

    /**
     * Implementation specific type that is not presented in language syntax
     * It represents any type
     * It can be for example to show any possible function that can return any result
     *
     * Example: func () any
     */
    object Any : Type() {
        override fun toString(): String = "Any"
    }

    data class Func(
        val paramTypes: List<Type>,
        val returnTypes: List<Type>
    ) : Type() {
        override fun toString(): String = super.toString()
    }

    data class ArrayType(
        val length: Int,
        val elementType: Type
    ) : Type()

    data class PointerType(
        val baseType: Type
    ) : Type()
}

fun TypeNode.toType(): Type = when(this) {
    is IntegerTypeNode -> Type.IntegerType
    is DoubleTypeNode -> Type.DoubleType
    is StringTypeNode -> Type.StringType
    is BoolTypeNode -> Type.BoolType
    is ArrayTypeNode -> Type.ArrayType(length = 1, elementType = elementType.toType())
    is FunctionTypeNode -> Type.Func(
        paramTypes = elementType.parameterNodes.map { it.type.toType() },
        returnTypes = elementType.resultNode.map { it.toType() }
    )
    is PointerTypeNode -> Type.PointerType(baseType = baseType.toType())
}
