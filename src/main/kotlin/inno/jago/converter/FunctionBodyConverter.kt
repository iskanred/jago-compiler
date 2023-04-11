package inno.jago.converter

import GoParser
import inno.jago.ast.statement.BlockStatementNode
import inno.jago.ast.statement.SimpleStatementNode
import inno.jago.ast.statement.StatementNode
import inno.jago.converter.statement.simple_statement.toSimpleStatementNode

fun GoParser.FunctionBodyContext.toBlockStatementNode(): BlockStatementNode {
    val statementNodes = mutableListOf<StatementNode>()
    block().statementList().statement().forEach { statement ->
        with(statement) {
            simpleStmt()?.let {
                statementNodes.add(it.toSimpleStatementNode())
            }
            block()?.let {
                TODO()
            }
            declaration()?.let {
                TODO()
            }
            returnStmt()?.let {
                TODO()
            }
            breakStmt()?.let {
                TODO()
            }
            continueStmt()?.let {
                TODO()
            }
            ifStmt()?.let {
                TODO()
            }
            forStmt()?.let {
                TODO()
            }
        }
    }
    return BlockStatementNode(toPos(), statementNodes)
}

