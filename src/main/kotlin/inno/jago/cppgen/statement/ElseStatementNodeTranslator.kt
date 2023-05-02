package inno.jago.cppgen.statement

import inno.jago.ast.model.statement.ElseIfStatementNode
import inno.jago.ast.model.statement.ElseStatementNode
import inno.jago.ast.model.statement.SimpleElseStatementNode

fun ElseStatementNode.translateToCode(): String = when (this) {
    is ElseIfStatementNode -> {
        "else ${ifStmt.translateToCode()}"
    }

    is SimpleElseStatementNode -> {
        "else ${block.translateToCode()}"
    }
}
