package inno.jago.semantic.analyzer.statement

import inno.jago.ast.model.statement.AssignmentNode
import inno.jago.ast.model.statement.BlockStatementNode
import inno.jago.ast.model.statement.BreakStatementNode
import inno.jago.ast.model.statement.ContinueStatementNode
import inno.jago.ast.model.statement.DeclarationStatementNode
import inno.jago.ast.model.statement.ElseStatementNode
import inno.jago.ast.model.statement.EmptyStatementNode
import inno.jago.ast.model.statement.ExpressionStatementNode
import inno.jago.ast.model.statement.ForStatementNode
import inno.jago.ast.model.statement.IfStatementNode
import inno.jago.ast.model.statement.IncDecStatementNode
import inno.jago.ast.model.statement.ReturnStatementNode
import inno.jago.ast.model.statement.ShortVarDeclNode
import inno.jago.ast.model.statement.StatementNode
import inno.jago.semantic.ReturnInGlobalScopeException
import inno.jago.semantic.WrongTypeException
import inno.jago.semantic.analyzer.expression.toSemanticEntity
import inno.jago.semantic.model.EntityType
import inno.jago.semantic.model.ScopeNode
import inno.jago.semantic.model.SemanticEntity
import inno.jago.semantic.model.Type
import inno.jago.semantic.toType

fun StatementNode.toSemanticEntity(scope: ScopeNode): SemanticEntity = when (this) {
    is AssignmentNode -> TODO()
    is IfStatementNode -> toSemanticEntity(scope)
    is ElseStatementNode -> TODO() // NOT NEEDED
    is ExpressionStatementNode -> toSemanticEntity(scope)
    is IncDecStatementNode -> toSemanticEntity(scope)
    is BlockStatementNode -> toSemanticEntity(scope)
    is BreakStatementNode -> toSemanticEntity(scope)
    is ContinueStatementNode -> toSemanticNode(scope)
    is DeclarationStatementNode -> TODO()
    is ReturnStatementNode -> toSemanticEntity(scope)
    is EmptyStatementNode -> toSemanticEntity()
    is ForStatementNode -> toSemanticEntity(scope)
    is ShortVarDeclNode -> toSemanticEntity(scope)
}

private fun IncDecStatementNode.toSemanticEntity(scope: ScopeNode): SemanticEntity {
    val expressionEntity = expression.toSemanticEntity(scope)
    if (expressionEntity.type !is Type.NumberType) {
        throw WrongTypeException(Type.NumberType(), actual = expressionEntity)
    }
    return SemanticEntity(
        type = expressionEntity.type,
        pos = pos,
        entityType = EntityType.STATEMENT
    )
}

private fun ReturnStatementNode.toSemanticEntity(scope: ScopeNode) = SemanticEntity(
    type = expressions.toType { it.toSemanticEntity(scope).type },
    pos = pos,
    entityType = EntityType.STATEMENT
).also { entity ->
    val expectedReturnType: Type = scope.getExpectedReturnType() ?: throw ReturnInGlobalScopeException(pos)
    if (expectedReturnType != entity.type) {
        throw WrongTypeException(expectedReturnType, actual = entity)
    }
}

private fun EmptyStatementNode.toSemanticEntity() = SemanticEntity (
    type = Type.AnyType,
    pos = pos,
    entityType = EntityType.STATEMENT,
)
