package inno.jago.converter.statement

import inno.jago.ast.statement.BreakStatementNode
import inno.jago.ast.statement.ContinueStatementNode
import inno.jago.converter.common.toPos

fun GoParser.BreakStmtContext.toBreakStatementNode() = BreakStatementNode(toPos())

fun GoParser.ContinueStmtContext.toContinueStatementNode() = ContinueStatementNode(toPos())
