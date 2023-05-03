package inno.jago.cppgen.statement

import inno.jago.ast.model.statement.BlockStatementNode
import inno.jago.ast.model.statement.BreakStatementNode
import inno.jago.ast.model.statement.ContinueStatementNode
import inno.jago.ast.model.statement.DeclarationStatementNode
import inno.jago.ast.model.statement.ElseStatementNode
import inno.jago.ast.model.statement.ForStatementNode
import inno.jago.ast.model.statement.IfStatementNode
import inno.jago.ast.model.statement.ReturnStatementNode
import inno.jago.ast.model.statement.SimpleStatementNode
import inno.jago.ast.model.statement.StatementNode
import inno.jago.common.UnreachableCodeException
import inno.jago.cppgen.declaration.translateToCode

fun StatementNode.translateToCode(): String = when (this) {
    is BlockStatementNode -> "{ ${block.map { it.translateToCode() }.joinToString(separator = "") { it }} }"

    is DeclarationStatementNode -> declaration.translateToCode()

    is ReturnStatementNode -> translateToCode()

    is BreakStatementNode -> "break;"

    is ContinueStatementNode -> "continue;"

    is IfStatementNode -> translateToCode()

    is ElseStatementNode -> translateToCode()

    is ForStatementNode -> translateToCode()

    is SimpleStatementNode -> translateToCode()
}

