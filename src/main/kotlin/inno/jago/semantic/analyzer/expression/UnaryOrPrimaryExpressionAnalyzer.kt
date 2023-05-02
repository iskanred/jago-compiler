@file:Suppress("ThrowsCount")
package inno.jago.semantic.analyzer.expression

import inno.jago.ast.model.expression.unary_expression.ApplicationExpressionNode
import inno.jago.ast.model.expression.unary_expression.ConversionNode
import inno.jago.ast.model.expression.unary_expression.IndexExpressionNode
import inno.jago.ast.model.expression.unary_expression.PrimaryExpressionNode
import inno.jago.ast.model.expression.unary_expression.UnaryExpressionNode
import inno.jago.ast.model.expression.unary_expression.UnaryOperators
import inno.jago.ast.model.expression.unary_expression.UnaryOrPrimaryExpressionNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.OperandNode
import inno.jago.common.EntityNotSupportedException
import inno.jago.common.JaGoException
import inno.jago.common.UnreachableCodeException
import inno.jago.semantic.NonCastableTypeException
import inno.jago.semantic.SemanticException
import inno.jago.semantic.WrongTypeException
import inno.jago.semantic.model.ExpressionEntity
import inno.jago.semantic.model.ScopeNode
import inno.jago.semantic.model.Type
import inno.jago.semantic.model.toType

fun UnaryOrPrimaryExpressionNode.toSemanticEntity(scope: ScopeNode): ExpressionEntity = when (this) {
    is UnaryExpressionNode -> toSemanticEntity(scope)
    is PrimaryExpressionNode -> toSemanticEntity(scope)
}

@Suppress("CyclomaticComplexMethod")
fun UnaryExpressionNode.toSemanticEntity(scope: ScopeNode): ExpressionEntity = when (this.unaryOrPrimaryExpression) {
    is PrimaryExpressionNode -> this.unaryOrPrimaryExpression.toSemanticEntity(scope)
    is UnaryExpressionNode -> {
        if (operator == null) {
            throw JaGoException("Unary operator is null")
        }
        this.unaryOrPrimaryExpression.toSemanticEntity(scope).also {
            when (operator.operator) {
                UnaryOperators.PLUS, UnaryOperators.MINUS -> {
                    if (it.type !is Type.NumberType) {
                        throw WrongTypeException(Type.NumberType(), actualType = it.type, pos = pos)
                    }
                }
                UnaryOperators.EXCLAMATION -> {
                    if (it.type !is Type.BoolType) {
                        throw WrongTypeException(Type.BoolType, actualType = it.type, pos = pos)
                    }
                }
                UnaryOperators.CARET -> {
                    if (it.type !is Type.IntegerType) {
                        throw WrongTypeException(Type.IntegerType, actualType = it.type, pos = pos)
                    }
                }
                UnaryOperators.ASTERISK -> {
                    if (it.type !is Type.PointerType) {
                        throw WrongTypeException(Type.PointerType(Type.AnyType), actualType = it.type, pos = pos)
                    }
                }
                UnaryOperators.AMPERSAND -> {
                    TODO()
//                    if (it is NamedEntity) {
//                        throw WrongTypeException(Type.EquatableTypes, actualType = it.type, pos = pos)
//                    }
                }
                UnaryOperators.RECEIVE -> throw EntityNotSupportedException("Unary operator <-")
            }
        }
    }
}

@Suppress("CyclomaticComplexMethod")
fun PrimaryExpressionNode.toSemanticEntity(scope: ScopeNode): ExpressionEntity = when (this) {
    is ConversionNode -> toSemanticEntity(scope)
    is IndexExpressionNode -> toSemanticEntity(scope)
    is ApplicationExpressionNode -> toSemanticEntity(scope)
    is OperandNode -> toSemanticEntity(scope)
    else -> throw UnreachableCodeException()
}

fun ConversionNode.toSemanticEntity(scope: ScopeNode): ExpressionEntity {
    val expressionEntity = expression.toSemanticEntity(scope)
    val conversionType = type.toType()

    if (expressionEntity.type != conversionType) {
        if (expressionEntity.type !is Type.NumberType || conversionType !is Type.NumberType) {
            throw NonCastableTypeException(
                from = expressionEntity.type,
                to = conversionType,
                pos = pos
            )
        }
        return ExpressionEntity(type = conversionType)
    }
    return expressionEntity
}

fun IndexExpressionNode.toSemanticEntity(scope: ScopeNode): ExpressionEntity {
    val exprEntity = primaryExpression.toSemanticEntity(scope)
    val indexEntity = expression.toSemanticEntity(scope)

    if (indexEntity.type !is Type.IntegerType) {
        throw WrongTypeException(Type.IntegerType, actualType = indexEntity.type, pos = pos)
    }

    if (exprEntity.type !is Type.ArrayType) {
        throw WrongTypeException(Type.ArrayType(0, Type.AnyType), actualType = exprEntity.type, pos = pos)
    }

    return ExpressionEntity(type = exprEntity.type)
}

fun ApplicationExpressionNode.toSemanticEntity(scope: ScopeNode): ExpressionEntity {
    val functionEntity = leftExpression.toSemanticEntity(scope)
    val args = expressions.map { it.toSemanticEntity(scope) }

    when (functionEntity.type) {
        is Type.FuncType -> {
            // длина
            if (functionEntity.type.paramTypes.size != args.size) {
                throw SemanticException(
                    "Number of arguments does not match number of parameters at $pos"
                )
            }
            // проверка соответствия типов
            args.forEachIndexed { i, argEntity ->
                if (functionEntity.type.paramTypes[i] != argEntity.type) {
                    throw WrongTypeException(
                        functionEntity.type.paramTypes[i],
                        actualType = functionEntity.type,
                        pos = pos
                    )
                }
            }

            // возвращаем то, что вернула функция
            return ExpressionEntity(type = functionEntity.type.returnType)
        }
        else -> throw WrongTypeException(
            Type.FuncType(args.map { it.type }, Type.AnyType),
            actualType = functionEntity.type,
            pos = pos
        )
    }
}
