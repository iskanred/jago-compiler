package inno.jago.semantic

import inno.jago.ast.decl.ConstDeclarationNode
import inno.jago.ast.decl.FunctionDeclarationNode
import inno.jago.ast.decl.TopLevelDeclNode
import inno.jago.ast.decl.VarDeclarationNode
import inno.jago.ast.global.SourceFileNode

class TypeChecker(
    private val sourceFileNode: SourceFileNode
) {

    fun startTypeCheck() = sourceFileNode.typecheck(ScopeNode(name = "GLOBAL", parent = null))

    private fun SourceFileNode.typecheck(scope: ScopeNode) {
        importNodes.map { /* todo */ }
        scope.addUniqueEntities(topLevelDecls) { it.toSemanticEntity(scope) }
    }

    private fun TopLevelDeclNode.toSemanticEntity(scope: ScopeNode): SemanticEntity = when(this) {
        is FunctionDeclarationNode -> toSemanticEntity(scope)
        is ConstDeclarationNode -> toSemanticEntity(scope)
        is VarDeclarationNode -> toSemanticEntity(scope)
    }

    private fun FunctionDeclarationNode.toSemanticEntity(scope: ScopeNode): SemanticEntity {
        TODO()
    }

    private fun ConstDeclarationNode.toSemanticEntity(scope: ScopeNode): SemanticEntity {
        TODO()
    }

    private fun VarDeclarationNode.toSemanticEntity(scope: ScopeNode): SemanticEntity {
        TODO()
    }
}
