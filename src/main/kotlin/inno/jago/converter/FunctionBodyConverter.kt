package inno.jago.converter

import GoParser
import inno.jago.ast.expression.ExpressionNode
import inno.jago.ast.statement.BlockStatementNode
import inno.jago.ast.statement.ReturnStatementNode
import inno.jago.ast.statement.StatementNode
import inno.jago.converter.expression.toExpressionNode
import inno.jago.converter.statement.toSimpleStatementNode
import inno.jago.converter.statement.toDeclarationStatementNode
import inno.jago.converter.statement.toBlockStatementNode
import inno.jago.converter.statement.toBreakStatementNode
import inno.jago.converter.statement.toContinueStatementNode

fun GoParser.FunctionBodyContext.toBlockStatementNode(): BlockStatementNode {
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
                statementNodes.add(it.toDeclarationStatementNode())
            }
            returnStmt()?.let {
                statementNodes.add(it.toReturnStatementNode())
            }
            breakStmt()?.let {
                statementNodes.add(it.toBreakStatementNode())
            }
            continueStmt()?.let {
                statementNodes.add(it.toContinueStatementNode())
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

fun GoParser.ReturnStmtContext.toReturnStatementNode() : ReturnStatementNode {
    val expressionNodes = mutableListOf<ExpressionNode>()

    expressionList()?.expression()?.forEach {
        expressionNodes.add(it.toExpressionNode())
    }

    return ReturnStatementNode(toPos(), expressionNodes)
}
