package inno.jago.converter.statement

import GoParser
import inno.jago.ast.statement.BlockStatementNode
import inno.jago.ast.statement.StatementNode
import inno.jago.converter.toDeclarationNodes
import inno.jago.converter.toPos
import inno.jago.converter.toReturnStatementNode

fun GoParser.BlockContext.toBlockStatementNode() = BlockStatementNode(
    pos = toPos(),
    block = statementList().statement().map { it.toStatementNode() }
)

fun GoParser.StatementContext.toStatementNode(): StatementNode {
    val statementNodes = block().statementList().statement().map { statementContext ->
        statementContext.apply {
            simpleStmt()?.let { simpleStmtContext ->
                return@map simpleStmtContext.toSimpleStatementNode()
            }
            block()?.let { blockContext ->
                return@map blockContext.toBlockStatementNode()
            }
            declaration()?.let { declarationContext ->
                return@map declarationContext.toDeclarationNodes()
            }
            returnStmt()?.let { returnStmt ->
                return@map returnStmt.toReturnStatementNode()
            }
            breakStmt()?.let { breakStmtContext ->
                return@map breakStmtContext.toBreakStatementNode()
            }
            continueStmt()?.let { continueStmtContext ->
                return@map continueStmtContext.toContinueStatementNode()
            }
            ifStmt()?.let { ifStmtContext ->
                return@map TODO()
            }
            forStmt()?.let { forStmtContext ->
                return@map TODO()
            }
        }
    }

    return BlockStatementNode(toPos(), statementNodes)
}
