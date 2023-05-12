package inno.jago.semantic.analyzer.expression

import inno.jago.ast.UnknownTypeException
import inno.jago.ast.model.expression.binary_expression.AddOperator
import inno.jago.ast.model.expression.binary_expression.AddOperators
import inno.jago.ast.model.expression.binary_expression.BinaryExpression
import inno.jago.ast.model.expression.binary_expression.LogicOperator
import inno.jago.ast.model.expression.binary_expression.MulOperator
import inno.jago.ast.model.expression.binary_expression.MulOperators
import inno.jago.ast.model.expression.binary_expression.RelationOperator
import inno.jago.ast.model.expression.binary_expression.RelationOperators
import inno.jago.semantic.WrongTypeException
import inno.jago.semantic.model.ExpressionEntity
import inno.jago.semantic.model.ScopeNode
import inno.jago.semantic.model.Type

@Suppress("ThrowsCount", "CyclomaticComplexMethod", "LongMethod", "NestedBlockDepth", "ReturnCount")
fun BinaryExpression.toSemanticEntity(scope: ScopeNode): ExpressionEntity {
    val left = leftExpression.toSemanticEntity(scope)
    val right = rightExpression.toSemanticEntity(scope)

    when (binaryOperator) {
//      EQUALS, NOT_EQUALS, LESS, LESS_OR_EQUALS, GREATER, GREATER_OR_EQUALS
        is RelationOperator -> {
            listOf(left, right).forEach {
                when (binaryOperator.operator) {
                    RelationOperators.EQUALS, RelationOperators.NOT_EQUALS -> {
                        if (!it.type.isEquatable()) {
                            throw WrongTypeException(Type.EquatableTypes, actualType = it.type, pos = pos)
                        }
                    }

                    else -> if (!it.type.isComparable()) {
                        throw WrongTypeException(Type.ComparableTypes, actualType = it.type, pos = pos)
                    }
                }
            }
            if (left.type is Type.NamedType && !scope.hasSuchType(left.type)) {
                throw UnknownTypeException(pos = pos, entityName = left.type.name)
            }
            if (right.type is Type.NamedType && !scope.hasSuchType(right.type)) {
                throw UnknownTypeException(pos = pos, entityName = right.type.name)
            }
            if (left.type != right.type) {
                throw WrongTypeException(left.type, actualType = right.type, pos = pos)
            }

            return ExpressionEntity(type = Type.BoolType)
        }

//        *    product                integers, floats
//        /    quotient               integers, floats
//        %    remainder              integers
//        &    bitwise AND            integers
//        &^   bit clear (AND NOT)    integers
//        <<   left shift             integer << integer >= 0
//        >>   right shift            integer >> integer >= 0
//        ASTERISK, DIV, MOD, LSHIFT, RSHIFT, AMPERSAND, BIT_CLEAR
        is MulOperator -> {
            listOf(left, right).forEach {
                val expectedTypes = when (binaryOperator.operator) {
                    MulOperators.ASTERISK, MulOperators.DIV -> listOf(Type.IntegerType, Type.DoubleType)
                    else -> listOf(Type.IntegerType)
                }

                if (it.type !in expectedTypes) {
                    @Suppress("SpreadOperator")
                    throw WrongTypeException(*expectedTypes.toTypedArray(), actualType = it.type, pos = pos)
                }
            }

            if (left.type != right.type) {
                throw WrongTypeException(left.type, actualType = right.type, pos = pos)
            }

            return ExpressionEntity(type = left.type)
        }

//        +    sum                    integers, floats, strings
//        -    difference             integers, floats
//        |    bitwise OR             integers
//        ^    bitwise XOR            integers
//        PLUS, MINUS, OR, CARET
        is AddOperator -> {
            listOf(left, right).forEach {
                val expectedTypes = when (binaryOperator.operator) {
                    AddOperators.PLUS -> listOf(Type.IntegerType, Type.DoubleType, Type.StringType)
                    AddOperators.MINUS -> listOf(Type.IntegerType, Type.DoubleType)
                    AddOperators.OR, AddOperators.CARET -> listOf(Type.IntegerType)
                }

                if (it.type !in expectedTypes) {
                    @Suppress("SpreadOperator")
                    throw WrongTypeException(*expectedTypes.toTypedArray(), actualType = it.type, pos = pos)
                }
            }

            if (left.type != right.type) {
                throw WrongTypeException(left.type, actualType = right.type, pos = pos)
            }

            return ExpressionEntity(type = left.type)
        }

//        &&    conditional AND    p && q  is  "if p then q else false"
//        ||    conditional OR     p || q  is  "if p then true else q"
//        LOGIC_OR, LOGIC_AND
        is LogicOperator -> {
            listOf(left, right).forEach {
                if (it.type != Type.BoolType) {
                    throw WrongTypeException(Type.BoolType, actualType = it.type, pos = pos)
                }
            }

            return ExpressionEntity(type = Type.BoolType)
        }
    }
}

fun Type.isEquatable(): Boolean = when(this) {
    is Type.IntegerType,
    is Type.DoubleType,
    is Type.StringType,
    is Type.BoolType,
    is Type.PointerType -> true
    is Type.NamedType -> true
    is Type.StructType -> true
    is Type.ArrayType -> elementType.isEquatable()
    else -> false
}

fun Type.isComparable(): Boolean = when(this) {
    is Type.IntegerType, is Type.DoubleType, Type.StringType -> true
    else -> false
}
