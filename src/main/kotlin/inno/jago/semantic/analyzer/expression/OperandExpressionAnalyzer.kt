package inno.jago.semantic.analyzer.expression

import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.ExpressionOperandNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.IdentifierOperandNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.LiteralOperandNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.OperandNameNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.OperandNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.QualifiedIdentifierOperandNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.SimpleIdentifierOperandNode
import inno.jago.common.EntityNotSupportedException
import inno.jago.semantic.NoSuchEntityInCurrentScopeException
import inno.jago.semantic.model.ExpressionEntity
import inno.jago.semantic.model.ScopeNode

fun OperandNode.toSemanticEntity(scope: ScopeNode): ExpressionEntity = when(this) {
    is LiteralOperandNode -> toSemanticEntity(scope)
    is OperandNameNode -> toSemanticEntity(scope)
    is ExpressionOperandNode -> toSemanticEntity(scope)
}

private fun LiteralOperandNode.toSemanticEntity(scope: ScopeNode): ExpressionEntity =
    literalNode.toSemanticEntity(scope)

private fun OperandNameNode.toSemanticEntity(scope: ScopeNode): ExpressionEntity =
    name.toSemanticEntity(scope)

private fun ExpressionOperandNode.toSemanticEntity(scope: ScopeNode): ExpressionEntity =
    expression.toSemanticEntity(scope)

private fun IdentifierOperandNode.toSemanticEntity(scope: ScopeNode): ExpressionEntity = when(this) {
    is SimpleIdentifierOperandNode -> toSemanticEntity(scope)
    is QualifiedIdentifierOperandNode -> throw EntityNotSupportedException("Qualified identifiers")
}

private fun SimpleIdentifierOperandNode.toSemanticEntity(scope: ScopeNode): ExpressionEntity =
    ExpressionEntity(
        type = scope.findVisibleEntity(identifier)?.type
            ?: throw NoSuchEntityInCurrentScopeException(identifier = identifier, pos = pos)
    )
