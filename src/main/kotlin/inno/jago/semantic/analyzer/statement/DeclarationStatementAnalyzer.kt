package inno.jago.semantic.analyzer.statement

import inno.jago.ast.model.decl.ConstDeclarationNode
import inno.jago.ast.model.decl.VarDeclarationNode
import inno.jago.ast.model.statement.DeclarationStatementNode
import inno.jago.semantic.analyzer.declaration.toSemanticEntity
import inno.jago.semantic.model.ScopeNode

fun DeclarationStatementNode.toSemanticNode(scope: ScopeNode) = when (declaration) {
    is ConstDeclarationNode -> declaration.toSemanticEntity(scope)
    is VarDeclarationNode -> declaration.toSemanticEntity(scope)
}
