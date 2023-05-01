package inno.jago.ast.converter.statement

import GoParser
import inno.jago.ast.EntityNotSupportedException
import inno.jago.common.UnreachableCodeException
import inno.jago.ast.model.statement.BlockStatementNode
import inno.jago.ast.model.statement.StatementNode
import inno.jago.ast.converter.common.toPos

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
        throw EntityNotSupportedException("Defers")
    }
    goStmt()?.let {
        throw EntityNotSupportedException("Goroutines")
    }
    gotoStmt()?.let {
        throw EntityNotSupportedException("Goto construction")
    }
    switchStmt()?.let {
        throw EntityNotSupportedException("Switches")
    }
    selectStmt()?.let {
        throw EntityNotSupportedException("Goroutines")
    }
    fallthroughStmt()?.let {
        throw EntityNotSupportedException("Switches")
    }
    labeledStmt()?.let {
        throw EntityNotSupportedException("Labels")
    }
    throw UnreachableCodeException()
}
