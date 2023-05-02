package inno.jago.semantic.analyzer.statement

import inno.jago.ast.model.statement.ExpressionStatementNode
import inno.jago.semantic.analyzer.expression.toSemanticEntity
import inno.jago.semantic.model.ScopeNode
import inno.jago.semantic.model.StatementEntity

fun ExpressionStatementNode.toSemanticEntity(scope: ScopeNode) = StatementEntity().also {
    expression.toSemanticEntity(scope)
}
