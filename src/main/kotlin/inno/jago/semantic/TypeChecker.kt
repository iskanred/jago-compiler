package inno.jago.semantic

import inno.jago.ast.model.global.SourceFileNode
import inno.jago.semantic.analyzer.declaration.toSemanticEntity
import inno.jago.semantic.model.GlobalScopeNode
import inno.jago.semantic.model.ScopeNode

class TypeChecker(
    private val sourceFileNode: SourceFileNode
) {
    fun startTypeCheck() = sourceFileNode.typecheck(GlobalScopeNode())

    private fun SourceFileNode.typecheck(scope: ScopeNode) {
        topLevelDecls.forEach { it.toSemanticEntity(scope) }
    }
}
