package inno.jago.semantic.analyzer.statement

import inno.jago.ast.model.statement.BlockStatementNode
import inno.jago.semantic.model.EntityType
import inno.jago.semantic.model.ScopeNode
import inno.jago.semantic.model.SemanticEntity
import inno.jago.semantic.model.Type

fun BlockStatementNode.eachStatementToSemanticEntity(scope: ScopeNode) {
    block.forEach {
        it.toSemanticEntity(scope)
    }
}

fun BlockStatementNode.toSemanticEntity(scope: ScopeNode): SemanticEntity {
    val blockScope = scope.createNewSimpleBlockScope("'block' scope in ${scope.name}")
    eachStatementToSemanticEntity(blockScope)
    return SemanticEntity(
        type = Type.UnitType,
        pos = pos,
        entityType = EntityType.STATEMENT
    )
}
