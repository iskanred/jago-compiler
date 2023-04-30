package inno.jago.semantic.analyzer.expression

import inno.jago.ast.model.expression.ExpressionNode
import inno.jago.ast.model.expression.binary_expression.BinaryExpression
import inno.jago.ast.model.expression.unary_expression.ApplicationExpressionNode
import inno.jago.ast.model.expression.unary_expression.ConversionNode
import inno.jago.ast.model.expression.unary_expression.IndexExpressionNode
import inno.jago.ast.model.expression.unary_expression.PrimaryExpressionNode
import inno.jago.ast.model.expression.unary_expression.SelectorExpressionNode
import inno.jago.ast.model.expression.unary_expression.UnaryExpressionNode
import inno.jago.ast.model.expression.unary_expression.UnaryOperatorNode
import inno.jago.ast.model.expression.unary_expression.UnaryOrPrimaryExpression
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.ExpressionOperandNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.LiteralOperandNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.OperandNameNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.OperandNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.QualifiedIdentifierOperandNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.SimpleIdentifierOperandNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.literal_operand.IntegerLiteralNode
import inno.jago.exception.UnreachableCodeException
import inno.jago.semantic.NoSuchVariableInCurrentScopeException
import inno.jago.semantic.model.EntityType
import inno.jago.semantic.model.ScopeNode
import inno.jago.semantic.model.SemanticEntity
import inno.jago.semantic.model.Type

fun ExpressionNode.toSemanticEntity(scope: ScopeNode): SemanticEntity = when (this) {
    is UnaryOrPrimaryExpression -> toSemanticEntity(scope)
    is BinaryExpression -> TODO()
    else -> throw UnreachableCodeException()
}

fun UnaryOrPrimaryExpression.toSemanticEntity(scope: ScopeNode): SemanticEntity = when (this) {

    is UnaryExpressionNode -> {
        throw NotImplementedError()
    }

    is PrimaryExpressionNode -> {
        when (this) {
            is ExpressionOperandNode -> {
                throw NotImplementedError()
            }

            is ConversionNode -> {
                throw NotImplementedError()
            }

            is IndexExpressionNode -> {
                throw NotImplementedError()
            }

            is SelectorExpressionNode -> {
                throw NotImplementedError()
            }

            is ApplicationExpressionNode -> {
                throw NotImplementedError()
            }

            is LiteralOperandNode -> {
                when (literalNode) {
                    is IntegerLiteralNode -> SemanticEntity(Type.IntegerType, pos, EntityType.EXPRESSION, null)
                    else -> throw NotImplementedError()
                }
            }

            is OperandNameNode -> {
                when (name) {
                    is QualifiedIdentifierOperandNode -> {
                        scope.findVisibleEntity(name.qualifiedIdentifier.packageName)
                            ?: throw NoSuchVariableInCurrentScopeException(name.qualifiedIdentifier.packageName, pos)
                    }

                    is SimpleIdentifierOperandNode -> {
                        scope.findVisibleEntity(name.identifier) ?: throw NoSuchVariableInCurrentScopeException(
                            name.identifier,
                            pos
                        )
                    }
                }
            }

            is OperandNode -> {
                throw NotImplementedError()
            }

            else -> {
                throw NotImplementedError()
            }
        }
    }


    else -> throw NotImplementedError()
}

fun BinaryExpression.toSemanticEntity(scope: ScopeNode): SemanticEntity = TODO()
