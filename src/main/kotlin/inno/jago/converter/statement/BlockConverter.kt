package inno.jago.converter.statement

import GoParser
import inno.jago.EntityNotSupported
import inno.jago.UnreachableCodeException
import inno.jago.ast.statement.BlockStatementNode
import inno.jago.ast.statement.StatementNode
import inno.jago.converter.common.toPos

fun GoParser.BlockContext.toBlockStatementNode() = BlockStatementNode(
    pos = toPos(),
    block = statementList().statement().flatMap { it.toStatementNode() }
)

private fun GoParser.StatementContext.toStatementNode(): List<StatementNode> {
    simpleStmt()?.let { simpleStmtContext ->
        return listOf(simpleStmtContext.toSimpleStatementNode())
    }
    block()?.let { blockContext ->
        return listOf(blockContext.toBlockStatementNode())
    }
    declaration()?.let { declarationContext ->
        return declarationContext.toDeclarationStatementNodes()
    }
    returnStmt()?.let { returnStmt ->
        return listOf(returnStmt.toReturnStatementNode())
    }
    breakStmt()?.let { breakStmtContext ->
        return listOf(breakStmtContext.toBreakStatementNode())
    }
    continueStmt()?.let { continueStmtContext ->
        return listOf(continueStmtContext.toContinueStatementNode())
    }
    ifStmt()?.let { ifStmtContext ->
        return listOf(ifStmtContext.toIfStatementNode())
    }
    forStmt()?.let { forStmtContext ->
        return listOf(forStmtContext.toForStatementNode())
    }
    deferStmt()?.let {
        throw EntityNotSupported("Defers")
    }
    goStmt()?.let {
        throw EntityNotSupported("Goroutines")
    }
    gotoStmt()?.let {
        throw EntityNotSupported("Goto construction")
    }
    switchStmt()?.let {
        throw EntityNotSupported("Switches")
    }
    selectStmt()?.let {
        throw EntityNotSupported("Goroutines")
    }
    fallthroughStmt()?.let {
        throw EntityNotSupported("Switches")
    }
    labeledStmt()?.let {
        throw EntityNotSupported("Labels")
    }
    throw UnreachableCodeException()
}
