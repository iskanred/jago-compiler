package inno.jago.semantic

import inno.jago.ast.model.global.ImportNode
import inno.jago.ast.model.global.SourceFileNode
import inno.jago.semantic.analyzer.declaration.toSemanticEntity
import inno.jago.semantic.model.EntityType
import inno.jago.semantic.model.ScopeNode
import inno.jago.semantic.model.SemanticEntity
import inno.jago.semantic.model.Type

class TypeChecker(
    private val sourceFileNode: SourceFileNode
) {
    fun startTypeCheck() = sourceFileNode.typecheck(ScopeNode(name = "GLOBAL", parent = null))

    private fun SourceFileNode.typecheck(scope: ScopeNode) {
        importNodes.forEach { it.toSemanticEntity(scope) }
        topLevelDecls.forEach { it.toSemanticEntity(scope) }
    }

    private fun ImportNode.toSemanticEntity(scope: ScopeNode) = SemanticEntity(
        type = Type.ImportType,
        pos = pos,
        entityType = EntityType.IMPORT,
        identifier = name
    ).also { entity ->
        scope.addUniqueEntity(entity)
    }
}
