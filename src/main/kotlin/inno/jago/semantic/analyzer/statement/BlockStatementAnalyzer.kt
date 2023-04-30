package inno.jago.semantic.analyzer.statement

import inno.jago.ast.model.statement.BlockStatementNode
import inno.jago.semantic.model.ScopeNode

fun BlockStatementNode.eachStatementToSemanticEntity(scope: ScopeNode) {
    block.forEach {
        it.toSemanticEntity(scope)
    }
}
