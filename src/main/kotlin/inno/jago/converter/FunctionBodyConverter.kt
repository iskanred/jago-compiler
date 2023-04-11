package inno.jago.converter

import GoParser
import inno.jago.ast.expression.ExpressionNode
import inno.jago.ast.statement.BlockStatementNode
import inno.jago.ast.statement.ElseIfStatementNode
import inno.jago.ast.statement.IfStatementNode
import inno.jago.ast.statement.ReturnStatementNode
import inno.jago.ast.statement.SimpleElseStatementNode
import inno.jago.ast.statement.StatementNode
import inno.jago.converter.common.toPos
import inno.jago.converter.expression.toExpressionNode
import inno.jago.converter.statement.toSimpleStatementNode
import inno.jago.converter.statement.toDeclarationStatementNodes
import inno.jago.converter.statement.toBlockStatementNode
import inno.jago.converter.statement.toBreakStatementNode
import inno.jago.converter.statement.toContinueStatementNode
import inno.jago.converter.statement.toForStatementNode

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
                statementNodes.addAll(it.toDeclarationStatementNodes())
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
                statementNodes.add(it.toIfStatementNode())
            }
            forStmt()?.let {
                statementNodes.add(it.toForStatementNode())
            }
        }
    }

    return BlockStatementNode(pos = toPos(), block = statementNodes)
}

fun GoParser.ReturnStmtContext.toReturnStatementNode(): ReturnStatementNode {
    val expressionNodes = mutableListOf<ExpressionNode>()

    expressionList()?.expression()?.forEach {
        expressionNodes.add(it.toExpressionNode())
    }

    return ReturnStatementNode(pos = toPos(), expressions = expressionNodes)
}

fun GoParser.IfStmtContext.toIfStatementNode(): IfStatementNode {

    val simpleStatement = simpleStmt()?.toSimpleStatementNode()
    val expression = expression().toExpressionNode()
    val block = block()[0].toBlockStatementNode()

    val elseBranch = ifStmt()?.let {
        ElseIfStatementNode(pos = toPos(), ifStmt = it.toIfStatementNode())
    } ?:
    block()[1]?.toBlockStatementNode()?.let {
        SimpleElseStatementNode(pos = toPos(), block = it)
    }

    return IfStatementNode(
        pos = toPos(),
        simpleStatement = simpleStatement,
        expression = expression,
        block = block,
        elseBranch = elseBranch
    )
}
