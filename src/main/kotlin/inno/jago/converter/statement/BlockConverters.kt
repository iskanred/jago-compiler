package inno.jago.converter.statement

import GoParser
import inno.jago.ast.statement.BlockStatementNode
import inno.jago.ast.statement.StatementNode
import inno.jago.converter.toPos

fun GoParser.BlockContext.toBlockStatementNode(): BlockStatementNode {
    val statementNodes = mutableListOf<StatementNode>()

    statementList().statement().forEach { statement ->
        statementNodes.add(statement.toStatementNode())
    }

    return BlockStatementNode(toPos(), statementNodes)
}

fun GoParser.StatementContext.toStatementNode(): StatementNode {
    val statementNodes = mutableListOf<StatementNode>()
    block().statementList().statement().forEach { statement ->
        with(statement) {
            simpleStmt()?.let {
                statementNodes.add(it.toSimpleStatementNode())
            }
            block()?.let {
                statementNodes.add(it.toBlockStatementNode())
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
