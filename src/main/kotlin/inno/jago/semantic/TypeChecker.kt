package inno.jago.semantic

import inno.jago.ast.model.global.ImportNode
import inno.jago.ast.model.global.SourceFileNode
import inno.jago.semantic.analyzer.declaration.toSemanticEntity
import inno.jago.semantic.model.ImportEntity
import inno.jago.semantic.model.ScopeNode
import inno.jago.semantic.model.Type

class TypeChecker(
    private val sourceFileNode: SourceFileNode
) {
    fun startTypeCheck() = sourceFileNode.typecheck(ScopeNode.globalScopeNode)

    private fun SourceFileNode.typecheck(scope: ScopeNode) {
        importNodes.forEach { it.toSemanticEntity(scope) }
        topLevelDecls.forEach { it.toSemanticEntity(scope) }
    }

    private fun ImportNode.toSemanticEntity(scope: ScopeNode) = ImportEntity(
        type = Type.ImportType,
        identifier = name
    ).also {
        scope.addUniqueEntity(entity = it, pos = pos)
    }
}
