package inno.jago.semantic.analyzer.statement

import inno.jago.ast.model.statement.ExpressionStatementNode
import inno.jago.semantic.analyzer.expression.toSemanticEntity
import inno.jago.semantic.model.EntityType
import inno.jago.semantic.model.ScopeNode
import inno.jago.semantic.model.SemanticEntity
import inno.jago.semantic.model.Type

fun ExpressionStatementNode.toSemanticEntity(scope: ScopeNode): SemanticEntity {
    expression.toSemanticEntity(scope)
    return SemanticEntity(
        type = Type.UnitType,
        pos = pos,
        entityType = EntityType.STATEMENT
    )
}
