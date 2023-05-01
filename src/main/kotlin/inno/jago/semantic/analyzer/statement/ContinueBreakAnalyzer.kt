package inno.jago.semantic.analyzer.statement

import inno.jago.ast.model.statement.BreakStatementNode
import inno.jago.ast.model.statement.ContinueStatementNode
import inno.jago.semantic.BreakIsNotInLoopException
import inno.jago.semantic.ContinueIsNotInLoopException
import inno.jago.semantic.model.EntityType
import inno.jago.semantic.model.ScopeNode
import inno.jago.semantic.model.SemanticEntity
import inno.jago.semantic.model.Type


fun BreakStatementNode.toSemanticEntity(scopeNode: ScopeNode): SemanticEntity {
    if (scopeNode.hasLoopScope()) {
        throw BreakIsNotInLoopException(pos)
    }
    return SemanticEntity(
        type = Type.UnitType,
        pos = pos,
        entityType = EntityType.NO_IDENTIFIER
    )
}

fun ContinueStatementNode.toSemanticNode(scopeNode: ScopeNode): SemanticEntity {
    if (scopeNode.hasLoopScope()) {
        throw ContinueIsNotInLoopException(pos)
    }
    return SemanticEntity(
        type = Type.UnitType,
        pos = pos,
        entityType = EntityType.NO_IDENTIFIER
    )
}
