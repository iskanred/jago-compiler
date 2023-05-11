package inno.jago.semantic.model

import inno.jago.ast.model.type.ArrayTypeNode
import inno.jago.ast.model.type.BoolTypeNode
import inno.jago.ast.model.type.DoubleTypeNode
import inno.jago.ast.model.type.FunctionTypeNode
import inno.jago.ast.model.type.IntegerTypeNode
import inno.jago.ast.model.type.PointerTypeNode
import inno.jago.ast.model.type.StringTypeNode
import inno.jago.ast.model.type.StructTypeNode
import inno.jago.ast.model.type.TypeNameNode
import inno.jago.ast.model.type.TypeNode
import inno.jago.common.BOOL_TYPE_NAME
import inno.jago.common.DOUBLE_TYPE_NAME
import inno.jago.common.INT_TYPE_NAME
import inno.jago.common.STRING_TYPE_NAME
import inno.jago.lexer.Pos
import inno.jago.semantic.analyzer.signature.toType

sealed class Type {
    /**
     * Implementation specific type that is not presented in language syntax
     * It represents number type = int or float64
     * It can be used that some construction can only accept or return numbers
     */
    open class NumberType : Type() {
        override fun toString(): String = "$INT_TYPE_NAME or $DOUBLE_TYPE_NAME"
    }

    object IntegerType : NumberType() {
        override fun toString(): String = INT_TYPE_NAME
    }

    object DoubleType : NumberType() {
        override fun toString(): String = DOUBLE_TYPE_NAME
    }

    object StringType : Type() {
        override fun toString(): String = STRING_TYPE_NAME
    }

    object BoolType : Type() {
        override fun toString(): String = BOOL_TYPE_NAME
    }

    object EquatableTypes : Type() {
        override fun toString(): String =
            "$INT_TYPE_NAME or $DOUBLE_TYPE_NAME or$STRING_TYPE_NAME or $BOOL_TYPE_NAME " +
                "or pointer or array (with equatable elements)"
    }

    object ComparableTypes : Type() {
        override fun toString(): String = "$INT_TYPE_NAME or $DOUBLE_TYPE_NAME or $STRING_TYPE_NAME"
    }

    /**
     * Implementation specific type that is not presented in language syntax
     * It represents function that does not return anything
     *
     * Example:
     * func a(a int) {
     *     a++
     * }
     * Here function return type is UnitType
     */
    object UnitType : Type() {
        override fun toString(): String = "unit"
    }

    /**
     * Implementation specific type that is not presented in language syntax
     * It represents any type
     * It can be used to show any possible function that can return any result
     *
     * Example: func(any) any
     */
    object AnyType : Type() {
        override fun toString(): String = "any"
    }

    /**
     * Implementation specific type that is not presented in language syntax
     * It represents a structure of any number of any types
     * It can be used for functions that return several values
     *
     * Example: func() (int, int)
     * Here function return type is TupleType
     */
    data class TupleType(
        val elementTypes: List<Type>
    ) : Type() {
        override fun toString(): String = elementTypes.joinToString(prefix = "(", postfix = ")")
    }

    data class FuncType(
        val paramTypes: List<Type>,
        val returnType: Type
    ) : Type() {
        override fun toString(): String = "func(${paramTypes.joinToString()}) $returnType"
    }

    data class ArrayType(
        val length: Int,
        val elementType: Type
    ) : Type() {
        override fun toString(): String = "[$length]$elementType"
    }

    data class PointerType(
        val baseType: Type
    ) : Type() {
        override fun toString(): String = "*$baseType"
    }

    data class StructType(
        val pos: Pos,
        val fields: Map<String, Type>
    ) : Type()

    data class NamedType(
        val name: String
    ) : Type()
    /**
     * Implementation specific type.
     * Is necessary for function overloading
     */
    data class FuncTypesSumType(
        val funcTypes: List<FuncType>
    ) : Type()
}

fun TypeNode.toType(): Type = when(this) {
    is IntegerTypeNode -> Type.IntegerType
    is DoubleTypeNode -> Type.DoubleType
    is StringTypeNode -> Type.StringType
    is BoolTypeNode -> Type.BoolType
    is FunctionTypeNode -> signature.toType()
    is PointerTypeNode -> Type.PointerType(baseType = baseType.toType())
    is ArrayTypeNode -> Type.ArrayType(length = length.intValue, elementType = elementType.toType())
    is StructTypeNode -> Type.StructType(pos = pos, fields = fields.mapValues { it.value.toType() })
    is TypeNameNode -> Type.NamedType(name = identifier)
}
