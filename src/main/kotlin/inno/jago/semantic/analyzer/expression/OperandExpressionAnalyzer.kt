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
import inno.jago.semantic.model.EntityType
import inno.jago.semantic.model.ScopeNode
import inno.jago.semantic.model.SemanticEntity
import inno.jago.semantic.model.Type

fun OperandNode.toSemanticEntity(scope: ScopeNode): SemanticEntity = when(this) {
    is LiteralOperandNode -> toSemanticEntity()
    is OperandNameNode -> toSemanticEntity(scope)
    is ExpressionOperandNode -> toSemanticEntity(scope)
}

private fun LiteralOperandNode.toSemanticEntity(): SemanticEntity =
    literalNode.toSemanticEntity()

private fun OperandNameNode.toSemanticEntity(scope: ScopeNode): SemanticEntity =
    name.toSemanticEntity(scope)

private fun ExpressionOperandNode.toSemanticEntity(scope: ScopeNode): SemanticEntity =
    expression.toSemanticEntity(scope)

private fun IdentifierOperandNode.toSemanticEntity(scope: ScopeNode): SemanticEntity = when(this) {
    is SimpleIdentifierOperandNode -> toSemanticEntity(scope)
    is QualifiedIdentifierOperandNode -> toSemanticEntity(scope)
}

private fun SimpleIdentifierOperandNode.toSemanticEntity(scope: ScopeNode): SemanticEntity =
    scope.findVisibleEntity(identifier)
        ?: throw NoSuchEntityInCurrentScopeException(identifier = identifier, pos = pos)

private fun QualifiedIdentifierOperandNode.toSemanticEntity(scope: ScopeNode): SemanticEntity =
    qualifiedIdentifier.toSemanticEntity(scope)

private fun QualifiedIdentifierNode.toSemanticEntity(scope: ScopeNode): SemanticEntity {
    val packageNameEntity = scope.findVisibleEntity(packageName)
        ?: throw NoSuchEntityInCurrentScopeException(identifier = packageName, pos = pos)
    packageNameEntity.takeIf { it.type == Type.ImportType }
        ?: throw WrongTypeException(Type.ImportType, actual = packageNameEntity)

    // maybe here is necessary to load other packages type information
    //  and get entity for identifier

    return SemanticEntity(
        type = Type.AnyType,
        pos = pos,
        entityType = EntityType.VAR,
        identifier = "$packageName.$identifier"
    )
}
