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
    is IfStatementNode -> TODO()
    is ElseStatementNode -> TODO()
    is ExpressionStatementNode -> TODO()
    is IncDecStatementNode -> toSemanticEntity(scope)
    is BlockStatementNode -> TODO()
    is BreakStatementNode -> TODO()
    is ContinueStatementNode -> TODO()
    is DeclarationStatementNode -> TODO()
    is ReturnStatementNode -> toSemanticEntity(scope)
    is EmptyStatementNode -> toSemanticEntity(scope)
    is ForStatementNode -> TODO()
    is ShortVarDeclNode -> TODO()
}

private fun IncDecStatementNode.toSemanticEntity(scope: ScopeNode): SemanticEntity {
    val expressionEntity = expression.toSemanticEntity(scope)
    if (expressionEntity.type != Type.IntegerType || expressionEntity.type != Type.DoubleType) {
        throw WrongTypeException(Type.NumberType, expressionEntity)
    }
    return SemanticEntity(
        type = expressionEntity.type,
        pos = pos,
        entityType = EntityType.EXPRESSION
    )
}

private fun ReturnStatementNode.toSemanticEntity(scope: ScopeNode) = SemanticEntity(
    type = expressions.toType { it.toSemanticEntity(scope).type },
    pos = pos,
    entityType = EntityType.EXPRESSION
).also { entity ->
    val expectedReturnType: Type = scope.getExpectedReturnType() ?: throw ReturnInGlobalScopeException(pos)
    if (expectedReturnType != entity.type) {
        throw WrongTypeException(expectedType = expectedReturnType, actual = entity)
    }
}

private fun EmptyStatementNode.toSemanticEntity(scope: ScopeNode) = SemanticEntity (
    type = Type.AnyType,
    pos = pos,
    entityType = EntityType.EMPTY,
    identifier = ""
)


