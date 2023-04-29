package inno.jago.semantic

import inno.jago.ast.model.global.SourceFileNode
import inno.jago.semantic.converter.declaration.toSemanticEntity
import inno.jago.semantic.model.ScopeNode

class TypeChecker(
    private val sourceFileNode: SourceFileNode
) {

    fun startTypeCheck() = sourceFileNode.typecheck(ScopeNode(name = "GLOBAL", parent = null))

    private fun SourceFileNode.typecheck(scope: ScopeNode) {
        importNodes.map { /* todo */ }
        scope.addUniqueEntities(topLevelDecls) { it.toSemanticEntity(scope) }
    }
}
