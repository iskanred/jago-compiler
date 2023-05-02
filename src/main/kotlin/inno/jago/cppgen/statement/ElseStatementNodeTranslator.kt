package inno.jago.cppgen.statement

import inno.jago.ast.model.statement.ConditionalForStatementNode
import inno.jago.ast.model.statement.ElseIfStatementNode
import inno.jago.ast.model.statement.ElseStatementNode
import inno.jago.ast.model.statement.ForClauseStatementNode
import inno.jago.ast.model.statement.SimpleElseStatementNode
import inno.jago.ast.model.statement.SimpleStatementNode

fun ElseStatementNode.translateToCode(): String = when (this) {
    is ElseIfStatementNode -> {
        "else ${ifStmt.translateToCode()}"
    }

    is SimpleElseStatementNode -> {
        "else ${block.translateToCode()}"
    }
}
