package inno.jago.cppgen.statement

import inno.jago.ast.model.statement.AssignmentNode
import inno.jago.ast.model.statement.EmptyStatementNode
import inno.jago.ast.model.statement.ExpressionStatementNode
import inno.jago.ast.model.statement.IncDecStatementNode
import inno.jago.ast.model.statement.ShortVarDeclNode
import inno.jago.ast.model.statement.SimpleStatementNode

fun SimpleStatementNode.translateToCode(): String = when (this) {
    is AssignmentNode -> TODO()
    is EmptyStatementNode -> TODO()
    is ExpressionStatementNode -> TODO()
    is IncDecStatementNode -> TODO()
    is ShortVarDeclNode -> TODO()
}
