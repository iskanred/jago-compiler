@file:Suppress("ThrowsCount")
package inno.jago.semantic.analyzer.expression

import inno.jago.ast.model.expression.unary_expression.ApplicationExpressionNode
import inno.jago.ast.model.expression.unary_expression.ConversionNode
import inno.jago.ast.model.expression.unary_expression.IndexExpressionNode
import inno.jago.ast.model.expression.unary_expression.PrimaryExpressionNode
import inno.jago.ast.model.expression.unary_expression.UnaryExpressionNode
import inno.jago.ast.model.expression.unary_expression.UnaryOrPrimaryExpression
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.ExpressionOperandNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.LiteralOperandNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.OperandNameNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.OperandNode
import inno.jago.exception.JaGoException
import inno.jago.exception.UnreachableCodeException
import inno.jago.semantic.NoSuchEntityInCurrentScopeException
import inno.jago.semantic.NonCastableTypeException
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
    is PrimaryExpressionNode -> toSemanticEntity(scope)
    is UnaryExpressionNode -> {
        if (operator == null) {
            throw JaGoException("Unary operator is null")
        }
        toSemanticEntity(scope)
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
    val entity = expression.toSemanticEntity(scope)
    if (entity.type != type.toType()) {
        if (entity.type !is Type.NumberType || type.toType() !is Type.NumberType) {
            throw NonCastableTypeException(entity.type, type.toType(), pos)
        }
        return SemanticEntity(
            type = type.toType(),
            pos = pos,
            entityType = EntityType.EXPRESSION
        )
    }
    return entity
}

fun IndexExpressionNode.toSemanticEntity(scope: ScopeNode): SemanticEntity {
    val expr = primaryExpression.toSemanticEntity(scope)
    val index = expression.toSemanticEntity(scope)

    if (index.type !is Type.IntegerType) {
        throw WrongTypeException(Type.IntegerType, actual = index)
    }

    if (expr.identifier != null) {
        val exprFromScope = scope.findVisibleEntity(expr.identifier)
        if (exprFromScope != null) {
            if (exprFromScope.type != expr.type) {
                throw WrongTypeException(Type.ArrayType(-1, Type.AnyType), actual = index)
            }
        } else {
            throw NoSuchEntityInCurrentScopeException(expr.identifier, pos)
        }
    }

    if (expr.type !is Type.ArrayType) {
        throw WrongTypeException(Type.ArrayType(-1, Type.AnyType), actual = index)
    }

    return SemanticEntity(expr.type, pos, EntityType.EXPRESSION)
}

fun ApplicationExpressionNode.toSemanticEntity(scope: ScopeNode): SemanticEntity {
    val function = leftExpression.toSemanticEntity(scope)
    val args = expressions.map { it.toSemanticEntity(scope) }

    when (function.type) {
        is Type.FuncType -> {
            // длина
            if (function.type.paramTypes.size != args.size) {
                throw JaGoException("number of params is not equal to arguments number")
            }
            // проверка соответствия типов
            for (i in 0..args.size) {
                if (!function.type.paramTypes[i].equals(args[i])) {
                    throw WrongTypeException(function.type.paramTypes[i], actual = args[i])
                }
            }

            // возвращаем то, что вернула функция
            return SemanticEntity(
                type = function.type.returnType,
                pos = pos,
                entityType = EntityType.EXPRESSION,
            )
        }

        else -> throw WrongTypeException(Type.FuncType(args.map { it.type }, Type.AnyType), actual = function)
    }
}
