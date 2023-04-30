package inno.jago.semantic.analyzer.expression

import inno.jago.ast.model.expression.binary_expression.BinaryExpression
import inno.jago.ast.model.expression.binary_expression.LogicOperator
import inno.jago.exception.UnreachableCodeException
import inno.jago.semantic.WrongTypeException
import inno.jago.semantic.model.EntityType
import inno.jago.semantic.model.ScopeNode
import inno.jago.semantic.model.SemanticEntity
import inno.jago.semantic.model.Type

@Suppress("ThrowsCount")
fun BinaryExpression.toSemanticEntity(scope: ScopeNode): SemanticEntity {
    val left = leftExpression.toSemanticEntity(scope)
    val right = leftExpression.toSemanticEntity(scope)

    when (binaryOperator) {
        is LogicOperator -> {
            when {
                left.type !is Type.BoolType -> throw WrongTypeException(expectedType = Type.BoolType, actual = left)
                right.type !is Type.BoolType -> throw WrongTypeException(expectedType = Type.BoolType, actual = right)
            }
            return SemanticEntity(Type.BoolType, pos, EntityType.EXPRESSION)
        }
        else -> throw UnreachableCodeException()
    }
}
