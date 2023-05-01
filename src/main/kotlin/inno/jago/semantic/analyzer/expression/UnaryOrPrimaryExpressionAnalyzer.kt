@file:Suppress("ThrowsCount")
package inno.jago.semantic.analyzer.expression

import inno.jago.ast.model.expression.unary_expression.ApplicationExpressionNode
import inno.jago.ast.model.expression.unary_expression.ConversionNode
import inno.jago.ast.model.expression.unary_expression.IndexExpressionNode
import inno.jago.ast.model.expression.unary_expression.PrimaryExpressionNode
import inno.jago.ast.model.expression.unary_expression.UnaryExpressionNode
import inno.jago.ast.model.expression.unary_expression.UnaryOperators
import inno.jago.ast.model.expression.unary_expression.UnaryOrPrimaryExpression
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.ExpressionOperandNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.LiteralOperandNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.OperandNameNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.OperandNode
import inno.jago.common.EntityNotSupportedException
import inno.jago.common.JaGoException
import inno.jago.common.UnreachableCodeException
import inno.jago.semantic.NonCastableTypeException
import inno.jago.semantic.SemanticException
import inno.jago.semantic.model.EntityType
import inno.jago.semantic.WrongTypeException
import inno.jago.semantic.model.ScopeNode
import inno.jago.semantic.model.SemanticEntity
import inno.jago.semantic.model.Type
import inno.jago.semantic.model.toType

fun UnaryOrPrimaryExpression.toSemanticEntity(scope: ScopeNode): SemanticEntity = when (this) {
    is UnaryExpressionNode -> toSemanticEntity(scope)
    is PrimaryExpressionNode -> toSemanticEntity(scope)
}

fun UnaryExpressionNode.toSemanticEntity(scope: ScopeNode): SemanticEntity = when (this.unaryOrPrimaryExpression) {
    is PrimaryExpressionNode -> this.unaryOrPrimaryExpression.toSemanticEntity(scope)
    is UnaryExpressionNode -> {
        if (operator == null) {
            throw JaGoException("Unary operator is null")
        }
        this.unaryOrPrimaryExpression.toSemanticEntity(scope).also {
            when (operator.operator) {
                UnaryOperators.PLUS, UnaryOperators.MINUS -> {
                    if (it.type !is Type.NumberType) {
                        throw WrongTypeException(Type.NumberType(), actual = it)
                    }
                }
                UnaryOperators.EXCLAMATION -> {
                    if (it.type !is Type.BoolType) {
                        throw WrongTypeException(Type.BoolType, actual = it)
                    }
                }
                UnaryOperators.CARET -> {
                    if (it.type !is Type.IntegerType) {
                        throw WrongTypeException(Type.IntegerType, actual = it)
                    }
                }
                UnaryOperators.ASTERISK -> {
                    if (it.type !is Type.PointerType) {
                        throw WrongTypeException(Type.PointerType(Type.AnyType), actual = it)
                    }
                }
                UnaryOperators.AMPERSAND -> { // TODO: Здесь нужно проверить, что справа от амерасанда стоит переменная
                    if (it.type !is Type.EquatableTypes) {
                        throw WrongTypeException(Type.EquatableTypes, actual = it)
                    }
                }
                UnaryOperators.RECEIVE -> throw EntityNotSupportedException("Unary operator <-")
            }
        }
    }
}

@Suppress("CyclomaticComplexMethod")
fun PrimaryExpressionNode.toSemanticEntity(scope: ScopeNode): SemanticEntity = when (this) {
    is ExpressionOperandNode -> toSemanticEntity(scope)
    is ConversionNode -> toSemanticEntity(scope)
    is IndexExpressionNode -> toSemanticEntity(scope)
    is ApplicationExpressionNode -> toSemanticEntity(scope)
    is LiteralOperandNode -> toSemanticEntity(scope)
    is OperandNameNode -> toSemanticEntity(scope)
    is OperandNode -> TODO()
    else -> throw UnreachableCodeException()
}

fun ConversionNode.toSemanticEntity(scope: ScopeNode): SemanticEntity {
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
        return SemanticEntity(
            type = conversionType,
            pos = pos,
            entityType = EntityType.NO_IDENTIFIER
        )
    }
    return expressionEntity
}

fun IndexExpressionNode.toSemanticEntity(scope: ScopeNode): SemanticEntity {
    val exprEntity = primaryExpression.toSemanticEntity(scope)
    val indexEntity = expression.toSemanticEntity(scope)

    if (indexEntity.type !is Type.IntegerType) {
        throw WrongTypeException(Type.IntegerType, actual = indexEntity)
    }

    if (exprEntity.type !is Type.ArrayType) {
        throw WrongTypeException(Type.ArrayType(0, Type.AnyType), actual = exprEntity)
    }

    return SemanticEntity(exprEntity.type, pos, EntityType.NO_IDENTIFIER)
}

fun ApplicationExpressionNode.toSemanticEntity(scope: ScopeNode): SemanticEntity {
    val function = leftExpression.toSemanticEntity(scope)
    val args = expressions.map { it.toSemanticEntity(scope) }

    when (function.type) {
        is Type.FuncType -> {
            // длина
            if (function.type.paramTypes.size != args.size) {
                throw SemanticException(
                    "Number of params is not equal to number of arguments of function '${function.identifier}' at $pos"
                )
            }
            // проверка соответствия типов
            args.forEachIndexed { i, argEntity ->
                if (function.type.paramTypes[i] != argEntity.type) {
                    throw WrongTypeException(function.type.paramTypes[i], actual = argEntity)
                }
            }

            // возвращаем то, что вернула функция
            return SemanticEntity(
                type = function.type.returnType,
                pos = pos,
                entityType = EntityType.NO_IDENTIFIER,
            )
        }
        else -> throw WrongTypeException(Type.FuncType(args.map { it.type }, Type.AnyType), actual = function)
    }
}
