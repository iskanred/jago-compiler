package inno.jago.ast.converter.statement

import inno.jago.ast.EntityNotSupportedException
import inno.jago.common.UnreachableCodeException
import inno.jago.ast.model.statement.BlockStatementNode
import inno.jago.ast.model.statement.ConditionalForStatementNode
import inno.jago.ast.model.statement.ForClauseStatementNode
import inno.jago.ast.model.statement.ForStatementNode
import inno.jago.ast.model.statement.SimpleStatementNode
import inno.jago.ast.converter.expression.toExpressionNode
import inno.jago.ast.converter.common.toPos

fun GoParser.ForStmtContext.toForStatementNode(): ForStatementNode {
    // return ConditionalForStatementNode or ForClauseStatementNode,
    // if we get RangeClause, then return EntityNotSupported
    forClause()?.let {
        return ForClauseStatementNode(
            pos = toPos(),
            block = block().toBlockStatementNode(),
            initStatementNode = it.initStmt()?.toSimpleStatementNode(),
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
    throw EntityNotSupportedException("RangeClause")
}

private fun GoParser.InitStmtContext.toBlockStatementNode() = BlockStatementNode(
    pos = toPos(),
    block = listOf(simpleStmt().toSimpleStatementNode())
)

private fun GoParser.PostStmtContext.toSimpleStatementNode(): SimpleStatementNode =
    simpleStmt()?.toSimpleStatementNode()
        ?: throw UnreachableCodeException()
