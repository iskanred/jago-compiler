package inno.jago.semantic.analyzer.statement

import inno.jago.ast.model.statement.BlockStatementNode
import inno.jago.semantic.model.ScopeNode
import inno.jago.semantic.model.StatementEntity

fun BlockStatementNode.toSemanticEntity(scope: ScopeNode) = StatementEntity().also {
    val blockScope = scope.createNewSimpleBlockScope()
    block.forEach { it.toSemanticEntity(blockScope) }
}
