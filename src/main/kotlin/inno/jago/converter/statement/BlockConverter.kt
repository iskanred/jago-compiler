package inno.jago.converter.statement

import GoParser
import inno.jago.ast.statement.BlockStatementNode
import inno.jago.ast.statement.StatementNode
import inno.jago.converter.toIfStatementNode
import inno.jago.converter.common.toPos
import inno.jago.converter.toReturnStatementNode

fun GoParser.BlockContext.toBlockStatementNode() = BlockStatementNode(
    pos = toPos(),
    block = statementList().statement().map { it.toStatementNode() }
)

fun GoParser.StatementContext.toStatementNode(): StatementNode {
    val statementNodes = mutableListOf<StatementNode>()

    block().statementList().statement().forEach { statementContext ->
        with(statementContext) {
            simpleStmt()?.let { simpleStmtContext ->
                statementNodes.add(simpleStmtContext.toSimpleStatementNode())
            }
            block()?.let { blockContext ->
                statementNodes.add(blockContext.toBlockStatementNode())
            }
            declaration()?.let { declarationContext ->
                statementNodes.addAll(declarationContext.toDeclarationStatementNodes())
            }
            returnStmt()?.let { returnStmt ->
                statementNodes.add(returnStmt.toReturnStatementNode())
            }
            breakStmt()?.let { breakStmtContext ->
                statementNodes.add(breakStmtContext.toBreakStatementNode())
            }
            continueStmt()?.let { continueStmtContext ->
                statementNodes.add(continueStmtContext.toContinueStatementNode())
            }
            ifStmt()?.let { ifStmtContext ->
                statementNodes.add(ifStmtContext.toIfStatementNode())
            }
            forStmt()?.let { forStmtContext ->
                statementNodes.add(forStmtContext.toForStatementNode())
            }
        }
    }

    return BlockStatementNode(pos = toPos(), block = statementNodes)
}
