package inno.jago.semantic.analyzer.expression

import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.ExpressionOperandNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.IdentifierOperandNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.LiteralOperandNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.OperandNameNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.OperandNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.QualifiedIdentifierNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.QualifiedIdentifierOperandNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.SimpleIdentifierOperandNode
import inno.jago.semantic.NoSuchEntityInCurrentScopeException
import inno.jago.semantic.WrongTypeException
import inno.jago.semantic.model.ExpressionEntity
import inno.jago.semantic.model.ScopeNode
import inno.jago.semantic.model.Type

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
    is QualifiedIdentifierOperandNode -> toSemanticEntity(scope)
}

private fun SimpleIdentifierOperandNode.toSemanticEntity(scope: ScopeNode): ExpressionEntity =
    ExpressionEntity(
        type = scope.findVisibleEntity(identifier)?.type
            ?: throw NoSuchEntityInCurrentScopeException(identifier = identifier, pos = pos)
    )

private fun QualifiedIdentifierOperandNode.toSemanticEntity(scope: ScopeNode): ExpressionEntity =
    qualifiedIdentifier.toSemanticEntity(scope)

private fun QualifiedIdentifierNode.toSemanticEntity(scope: ScopeNode): ExpressionEntity {
    val packageNameEntity = scope.findVisibleEntity(packageName)
        ?: throw NoSuchEntityInCurrentScopeException(identifier = packageName, pos = pos)
    packageNameEntity.takeIf { it.type == Type.ImportType }
        ?: throw WrongTypeException(Type.ImportType, actualType = packageNameEntity.type, pos = pos)

    // maybe here is necessary to load other packages type information
    //  and get entity for identifier

    return ExpressionEntity(type = Type.AnyType)
}
