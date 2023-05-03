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
import inno.jago.common.UnreachableCodeException
import inno.jago.semantic.ReturnInWrongScopeException
import inno.jago.semantic.WrongTypeException
import inno.jago.semantic.analyzer.expression.toSemanticEntity
import inno.jago.semantic.model.ScopeNode
import inno.jago.semantic.model.SemanticEntity
import inno.jago.semantic.model.StatementEntity
import inno.jago.semantic.model.Type
import inno.jago.semantic.toType

fun StatementNode.toSemanticEntity(scope: ScopeNode): SemanticEntity = when (this) {
    is AssignmentNode -> toSemanticEntity(scope)
    is IfStatementNode -> toSemanticEntity(scope)
    is ExpressionStatementNode -> toSemanticEntity(scope)
    is IncDecStatementNode -> toSemanticEntity(scope)
    is BlockStatementNode -> toSemanticEntity(scope)
    is BreakStatementNode -> toSemanticEntity(scope)
    is ContinueStatementNode -> toSemanticNode(scope)
    is DeclarationStatementNode -> toSemanticNode(scope)
    is ReturnStatementNode -> toSemanticEntity(scope)
    is EmptyStatementNode -> toSemanticEntity()
    is ForStatementNode -> toSemanticEntity(scope)
    is ShortVarDeclNode -> toSemanticEntity(scope)
    is ElseStatementNode -> throw UnreachableCodeException() // NOT NEEDED
}

private fun IncDecStatementNode.toSemanticEntity(scope: ScopeNode) = StatementEntity().also { _ ->
    val expressionEntity = expression.toSemanticEntity(scope)
    if (expressionEntity.type !is Type.NumberType) {
        throw WrongTypeException(Type.NumberType(), actualType = expressionEntity.type, pos = pos)
    }
}

private fun ReturnStatementNode.toSemanticEntity(scope: ScopeNode) = StatementEntity().also { _ ->
    val expectedReturnType: Type = scope.findExpectedReturnType()
        ?: throw ReturnInWrongScopeException(pos)

    val type = expressions.toType { expr -> expr.toSemanticEntity(scope).type }

    if (expectedReturnType != type) {
        throw WrongTypeException(expectedReturnType, actualType = type, pos = pos)
    }
}

private fun EmptyStatementNode.toSemanticEntity() = StatementEntity()
