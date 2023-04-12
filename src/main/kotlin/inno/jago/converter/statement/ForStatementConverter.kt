package inno.jago.converter.statement

import inno.jago.exception.EntityNotSupported
import inno.jago.exception.UnreachableCodeException
import inno.jago.ast.statement.BlockStatementNode
import inno.jago.ast.statement.ConditionalForStatementNode
import inno.jago.ast.statement.ForClauseStatementNode
import inno.jago.ast.statement.ForStatementNode
import inno.jago.ast.statement.SimpleStatementNode
import inno.jago.converter.expression.toExpressionNode
import inno.jago.converter.common.toPos

fun GoParser.ForStmtContext.toForStatementNode(): ForStatementNode {
    // return ConditionalForStatementNode or ForClauseStatementNode,
    // if we get RangeClause, then return EntityNotSupported
    forClause()?.let {
        return ForClauseStatementNode(
            pos = toPos(),
            block = block().toBlockStatementNode(),
            initStatementNode = it.initStmt()?.toBlockStatementNode(),
            condition = it.condition()?.expression()?.toExpressionNode(),
            postStatementNode =  it.postStmt()?.toSimpleStatementNode()
        )
    }
    condition()?.let {
        return ConditionalForStatementNode(
            pos = toPos(),
            block = block().toBlockStatementNode(),
            condition = it.expression().toExpressionNode()
        )
    }
    throw EntityNotSupported("RangeClause")
}

private fun GoParser.InitStmtContext.toBlockStatementNode() = BlockStatementNode(
    pos = toPos(),
    block = listOf(simpleStmt().toSimpleStatementNode())
)

private fun GoParser.PostStmtContext.toSimpleStatementNode(): SimpleStatementNode =
    simpleStmt()?.toSimpleStatementNode()
        ?: throw UnreachableCodeException()
