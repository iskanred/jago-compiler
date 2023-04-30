package inno.jago.semantic.analyzer.expression

import inno.jago.ast.model.expression.ExpressionNode
import inno.jago.ast.model.expression.binary_expression.BinaryExpression
import inno.jago.ast.model.expression.binary_expression.LogicOperator
import inno.jago.ast.model.expression.binary_expression.RelationOperator
import inno.jago.ast.model.expression.unary_expression.UnaryOrPrimaryExpression
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.LiteralOperandNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.OperandNameNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.QualifiedIdentifierOperandNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.SimpleIdentifierOperandNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.literal_operand.IntegerLiteralNode
import inno.jago.exception.UnreachableCodeException
import inno.jago.semantic.NoSuchVariableInCurrentScopeException
import inno.jago.semantic.WrongTypeException
import inno.jago.semantic.model.EntityType
import inno.jago.semantic.model.ScopeNode
import inno.jago.semantic.model.SemanticEntity
import inno.jago.semantic.model.Type

fun ExpressionNode.toSemanticEntity(scope: ScopeNode): SemanticEntity = when(this) {
    is UnaryOrPrimaryExpression -> toSemanticEntity(scope)
    is BinaryExpression -> toSemanticEntity(scope)
    else -> throw UnreachableCodeException()
}

fun UnaryOrPrimaryExpression.toSemanticEntity(scope: ScopeNode): SemanticEntity = when(this) {
    is OperandNameNode -> {
        when (name) {
            is QualifiedIdentifierOperandNode -> {
                scope.findVisibleEntity(name.qualifiedIdentifier.packageName) ?: throw NoSuchVariableInCurrentScopeException(name.qualifiedIdentifier.packageName, pos)
            }
            is SimpleIdentifierOperandNode -> {
                scope.findVisibleEntity(name.identifier) ?: throw NoSuchVariableInCurrentScopeException(name.identifier, pos)
            }
        }
    }
    is LiteralOperandNode -> {
        when (literalNode) {
            is IntegerLiteralNode -> SemanticEntity(Type.IntegerType, pos, EntityType.EXPRESSION, null)
            else -> throw NotImplementedError()
        }
    }
    else -> throw NotImplementedError()
}

fun BinaryExpression.toSemanticEntity(scope: ScopeNode): SemanticEntity {
    val left = leftExpression.toSemanticEntity(scope)
    var right = leftExpression.toSemanticEntity(scope)

    when (binaryOperator) {
        is LogicOperator -> {
            if (left.type !is Type.BoolType) {
                throw WrongTypeException(expectedType = Type.BoolType, actual = left)
            }

            if (right.type !is Type.BoolType) {
                throw WrongTypeException(expectedType = Type.BoolType, actual = right)
            }

            return SemanticEntity(Type.BoolType, pos, EntityType.EXPRESSION)
        }
    }
}
