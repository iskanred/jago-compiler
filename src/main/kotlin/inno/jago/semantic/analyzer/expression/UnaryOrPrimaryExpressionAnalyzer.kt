@file:Suppress("ThrowsCount")
package inno.jago.semantic.analyzer.expression

import inno.jago.ast.model.expression.unary_expression.ApplicationExpressionNode
import inno.jago.ast.model.expression.unary_expression.ConversionNode
import inno.jago.ast.model.expression.unary_expression.IndexExpressionNode
import inno.jago.ast.model.expression.unary_expression.PrimaryExpressionNode
import inno.jago.ast.model.expression.unary_expression.SelectorExpressionNode
import inno.jago.ast.model.expression.unary_expression.UnaryExpressionNode
import inno.jago.ast.model.expression.unary_expression.UnaryOperators
import inno.jago.ast.model.expression.unary_expression.UnaryOrPrimaryExpressionNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.OperandNode
import inno.jago.common.EntityNotSupportedException
import inno.jago.common.JaGoException
import inno.jago.common.UnreachableCodeException
import inno.jago.semantic.NoSuchFunctionException
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

@Suppress("CyclomaticComplexMethod", "ReturnCount")
fun UnaryExpressionNode.toSemanticEntity(scope: ScopeNode): ExpressionEntity {
    if (operator == null) {
        throw JaGoException("Unary operator is null")
    }
    val semanticEntity = this.unaryOrPrimaryExpression.toSemanticEntity(scope)

    when (operator.operator) {
        UnaryOperators.PLUS, UnaryOperators.MINUS -> {
            if (semanticEntity.type !is Type.NumberType) {
                throw WrongTypeException(Type.NumberType(), actualType = semanticEntity.type, pos = pos)
            }
            return semanticEntity
        }

        UnaryOperators.EXCLAMATION -> {
            if (semanticEntity.type !is Type.BoolType) {
                throw WrongTypeException(Type.BoolType, actualType = semanticEntity.type, pos = pos)
            }
            return semanticEntity
        }

        UnaryOperators.CARET -> {
            if (semanticEntity.type !is Type.IntegerType) {
                throw WrongTypeException(Type.IntegerType, actualType = semanticEntity.type, pos = pos)
            }
            return semanticEntity
        }

        UnaryOperators.ASTERISK -> {
            if (semanticEntity.type !is Type.PointerType) {
                throw WrongTypeException(
                    Type.PointerType(baseType = Type.AnyType),
                    actualType = semanticEntity.type,
                    pos = pos
                )
            }
            return ExpressionEntity(semanticEntity.type.baseType)
        }

        UnaryOperators.AMPERSAND -> {
            return ExpressionEntity(Type.PointerType(semanticEntity.type))
        }

        UnaryOperators.RECEIVE -> throw EntityNotSupportedException("Unary operator <-")
    }
}

@Suppress("CyclomaticComplexMethod")
fun PrimaryExpressionNode.toSemanticEntity(scope: ScopeNode): ExpressionEntity = when (this) {
    is ConversionNode -> toSemanticEntity(scope)
    is IndexExpressionNode -> toSemanticEntity(scope)
    is SelectorExpressionNode -> toSemanticEntity(scope)
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

    return ExpressionEntity(type = exprEntity.type.elementType)
}

fun SelectorExpressionNode.toSemanticEntity(scope: ScopeNode): ExpressionEntity {
    val exprEntity = primaryExpression.toSemanticEntity(scope)

    if (exprEntity.type !is Type.StructType) {
        throw WrongTypeException(Type.StructType(pos = pos, fields = emptyMap()), actualType = exprEntity.type, pos = pos)
    }

    if (!exprEntity.type.fields.containsKey(selector)) {
        throw SemanticException("Struct(${exprEntity.type}) does not contain such field: $selector")
    }

    return ExpressionEntity(type = exprEntity.type.fields[selector]!!)
}

fun ApplicationExpressionNode.toSemanticEntity(scope: ScopeNode): ExpressionEntity {
    val functionEntity = leftExpression.toSemanticEntity(scope)
    val argTypes = expressions.map { it.toSemanticEntity(scope) }.map { it.type }

    when (functionEntity.type) {
        is Type.FuncType -> {
            // длина
            if (functionEntity.type.paramTypes.size != argTypes.size) {
                throw SemanticException(
                    "Number of arguments does not match number of parameters at $pos"
                )
            }
            // проверка соответствия типов
            argTypes.forEachIndexed { i, argType ->
                if (functionEntity.type.paramTypes[i] != argType) {
                    throw WrongTypeException(
                        functionEntity.type.paramTypes[i],
                        actualType = argType,
                        pos = pos
                    )
                }
            }
            // возвращаем то, что вернула функция
            return ExpressionEntity(type = functionEntity.type.returnType)
        }
        is Type.FuncTypesSumType -> {
            return functionEntity.type.funcTypes.firstOrNull { funcType ->
                argTypes.size == funcType.paramTypes.size &&
                        argTypes
                            .zip(funcType.paramTypes)
                            .all { (argType, paramType) -> argType == paramType }
            }?.returnType?.let {
                ExpressionEntity(type = it)
            } ?: throw NoSuchFunctionException(argTypes = argTypes, pos = pos)
        }
        else -> throw WrongTypeException(
            Type.FuncType(argTypes, Type.AnyType),
            actualType = functionEntity.type,
            pos = pos
        )
    }
}
