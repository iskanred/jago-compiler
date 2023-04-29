package inno.jago.converter.statement

import GoParser
import inno.jago.exception.UnreachableCodeException
import inno.jago.ast.statement.BlockStatementNode
import inno.jago.ast.statement.ElseIfStatementNode
import inno.jago.ast.statement.IfStatementNode
import inno.jago.ast.statement.ReturnStatementNode
import inno.jago.ast.statement.SimpleElseStatementNode
import inno.jago.converter.common.toPos
import inno.jago.converter.expression.toExpressionNode

fun GoParser.FunctionBodyContext.toBlockStatementNode(): BlockStatementNode =
    block()?.toBlockStatementNode()
        ?: throw UnreachableCodeException()

fun GoParser.ReturnStmtContext.toReturnStatementNode(): ReturnStatementNode {
    val expressionNodes = expressionList()?.expression()?.map {
        it.toExpressionNode()
    } ?: throw UnreachableCodeException()

    return ReturnStatementNode(pos = toPos(), expressions = expressionNodes)
}

fun GoParser.IfStmtContext.toIfStatementNode(): IfStatementNode {
    val simpleStatement = simpleStmt()?.toSimpleStatementNode()
    val expression = expression().toExpressionNode()
    val block = block()[0].toBlockStatementNode()

    val elseBranch = ifStmt()?.let {
        ElseIfStatementNode(pos = toPos(), ifStmt = it.toIfStatementNode())
    } ?:
    block().takeIf { it.size > 1 }?.get(1)?.toBlockStatementNode()?.let {
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
