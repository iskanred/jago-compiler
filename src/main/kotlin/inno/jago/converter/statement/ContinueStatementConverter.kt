package inno.jago.converter.statement

import inno.jago.ast.statement.ContinueStatementNode
import inno.jago.converter.toPos

fun GoParser.ContinueStmtContext.toContinueStatementNode(): ContinueStatementNode {
    return ContinueStatementNode(toPos())
}
