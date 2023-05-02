package inno.jago.semantic.analyzer.statement

import inno.jago.ast.model.statement.BreakStatementNode
import inno.jago.ast.model.statement.ContinueStatementNode
import inno.jago.semantic.BreakIsNotInLoopException
import inno.jago.semantic.ContinueIsNotInLoopException
import inno.jago.semantic.model.ScopeNode
import inno.jago.semantic.model.StatementEntity

fun BreakStatementNode.toSemanticEntity(scope: ScopeNode) = StatementEntity().also {
    if (scope.hasLoopScope()) {
        throw BreakIsNotInLoopException(pos)
    }
}

fun ContinueStatementNode.toSemanticNode(scope: ScopeNode) = StatementEntity().also {
    if (scope.hasLoopScope()) {
        throw ContinueIsNotInLoopException(pos)
    }
}
