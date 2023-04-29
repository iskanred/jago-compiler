package inno.jago.semantic

import inno.jago.ast.model.global.ImportNode
import inno.jago.ast.model.global.SourceFileNode
import inno.jago.semantic.analysis.declaration.toSemanticEntity
import inno.jago.semantic.model.EntityType
import inno.jago.semantic.model.ScopeNode
import inno.jago.semantic.model.SemanticEntity
import inno.jago.semantic.model.Type

class TypeChecker(
    private val sourceFileNode: SourceFileNode
) {

    fun startTypeCheck() = sourceFileNode.typecheck(ScopeNode(name = "GLOBAL", parent = null))

    private fun SourceFileNode.typecheck(scope: ScopeNode) {
        scope.addUniqueEntities(list = importNodes) { it.toSemanticEntity() }
        scope.addUniqueEntities(topLevelDecls) { it.toSemanticEntity(scope) }
    }

    private fun ImportNode.toSemanticEntity() = SemanticEntity(
        type = Type.ImportType,
        pos = pos,
        entityType = EntityType.IMPORT,
        identifier = name
    )
}
