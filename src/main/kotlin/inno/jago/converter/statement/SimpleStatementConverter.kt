package inno.jago.converter.statement

import GoParser
import inno.jago.EntityNotSupported
import inno.jago.UnreachableCodeException
import inno.jago.ast.statement.AssignmentNode
import inno.jago.ast.statement.ExpressionStatementNode
import inno.jago.ast.statement.IncDecStatementNode
import inno.jago.ast.statement.ShortVarDeclNode
import inno.jago.ast.statement.SimpleStatementNode
import inno.jago.converter.expression.toExpressionNode
import inno.jago.converter.common.toPos

fun GoParser.SimpleStmtContext.toSimpleStatementNode(): SimpleStatementNode {
    incDecStmt()?.let {
        return it.toIncDecStatementNode()
    }
    assignment()?.let {
        return it.toAssignmentNode()
    }
    shortVarDecl()?.let {
        return it.toShortVarDeclNode()
    }
    expressionStmt()?.let {
        return it.toExpressionStatementNode()
    }
    emptyStmt()?.let {
        throw EntityNotSupported("EmptySimpleStatement")
    }
    sendStmt()?.let {
        throw EntityNotSupported("SendSimpleStatement")
    }
    throw UnreachableCodeException()
}

fun GoParser.IncDecStmtContext.toIncDecStatementNode(): IncDecStatementNode {
    val incDec = PLUS_PLUS()?.let {
        IncDecStatementNode.IncDec.INC
    } ?:
    MINUS_MINUS()?.let {
        IncDecStatementNode.IncDec.DEC
    } ?:
    throw UnreachableCodeException()

    return IncDecStatementNode(
        pos = toPos(),
        expression = expression().toExpressionNode(),
        type = incDec
    )
}

fun GoParser.AssignmentContext.toAssignmentNode(): AssignmentNode = AssignmentNode(
    pos = toPos(),
    leftExpressions = expressionList()[0].expression().map { it.toExpressionNode() },
    rightExpressions = expressionList()[1].expression().map { it.toExpressionNode() }
)

fun GoParser.ShortVarDeclContext.toShortVarDeclNode(): ShortVarDeclNode = ShortVarDeclNode(
    pos = toPos(),
    identifierList = identifierList().IDENTIFIER().map { it.text },
    expression = expressionList().expression().map { it.toExpressionNode() }
)

fun GoParser.ExpressionStmtContext.toExpressionStatementNode(): ExpressionStatementNode = ExpressionStatementNode(
    pos = toPos(),
    expression = expression().toExpressionNode()
)
