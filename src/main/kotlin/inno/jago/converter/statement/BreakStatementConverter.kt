package inno.jago.converter.statement

import inno.jago.ast.statement.BreakStatementNode
import inno.jago.converter.toPos

fun GoParser.BreakStmtContext.toBreakStatementNode(): BreakStatementNode {
    return BreakStatementNode(toPos())
}
