package inno.jago.ast.converter.statement

import inno.jago.ast.model.statement.BreakStatementNode
import inno.jago.ast.model.statement.ContinueStatementNode
import inno.jago.ast.converter.common.toPos

fun GoParser.BreakStmtContext.toBreakStatementNode() = BreakStatementNode(toPos())

fun GoParser.ContinueStmtContext.toContinueStatementNode() = ContinueStatementNode(toPos())
