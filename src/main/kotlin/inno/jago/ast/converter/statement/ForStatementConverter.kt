package inno.jago.ast.converter.statement

import inno.jago.common.UnreachableCodeException
import inno.jago.ast.model.statement.ConditionalForStatementNode
import inno.jago.ast.model.statement.ForClauseStatementNode
import inno.jago.ast.model.statement.ForStatementNode
import inno.jago.ast.model.statement.SimpleStatementNode
import inno.jago.ast.converter.expression.toExpressionNode
import inno.jago.ast.converter.common.toPos
import inno.jago.common.EntityNotSupportedException

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

private fun GoParser.InitStmtContext.toSimpleStatementNode(): SimpleStatementNode =
    simpleStmt()?.toSimpleStatementNode()
        ?: throw UnreachableCodeException()
private fun GoParser.PostStmtContext.toSimpleStatementNode(): SimpleStatementNode =
    simpleStmt()?.toSimpleStatementNode()
        ?: throw UnreachableCodeException()
