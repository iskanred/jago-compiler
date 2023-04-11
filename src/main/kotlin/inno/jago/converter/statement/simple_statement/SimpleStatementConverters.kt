package inno.jago.converter.statement.simple_statement

import GoParser
import inno.jago.UnreachableCodeException
import inno.jago.ast.statement.AssignmentNode
import inno.jago.ast.statement.ExpressionStatementNode
import inno.jago.ast.statement.IncDecStatementNode
import inno.jago.ast.statement.ShortVarDeclNode
import inno.jago.ast.statement.SimpleStatementNode
import inno.jago.converter.expression.toExpressionNode
import inno.jago.converter.toPos

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
    throw UnreachableCodeException()
}

fun GoParser.IncDecStmtContext.toIncDecStatementNode(): IncDecStatementNode = IncDecStatementNode(
    toPos(),
    expression().toExpressionNode(),
    if (PLUS_PLUS() == null) IncDecStatementNode.IncDec.INC else IncDecStatementNode.IncDec.DEC
)

fun GoParser.AssignmentContext.toAssignmentNode(): AssignmentNode = AssignmentNode(
    toPos(),
    expressionList()[0].expression().map { it.toExpressionNode() },
    expressionList()[1].expression().map { it.toExpressionNode() }
)

fun GoParser.ShortVarDeclContext.toShortVarDeclNode(): ShortVarDeclNode = ShortVarDeclNode(
    toPos(),
    identifierList().IDENTIFIER().map { it.text },
    expressionList().expression().map { it.toExpressionNode() }
)

fun GoParser.ExpressionStmtContext.toExpressionStatementNode(): ExpressionStatementNode = ExpressionStatementNode(
    toPos(),
    expression().toExpressionNode()
)
