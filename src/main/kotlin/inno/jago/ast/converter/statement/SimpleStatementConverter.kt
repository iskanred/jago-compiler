package inno.jago.ast.converter.statement

import GoParser
import inno.jago.common.UnreachableCodeException
import inno.jago.ast.model.statement.AssignmentNode
import inno.jago.ast.model.statement.EmptyStatementNode
import inno.jago.ast.model.statement.ExpressionStatementNode
import inno.jago.ast.model.statement.IncDecStatementNode
import inno.jago.ast.model.statement.ShortVarDeclNode
import inno.jago.ast.model.statement.SimpleStatementNode
import inno.jago.ast.converter.expression.toExpressionNode
import inno.jago.ast.converter.common.toPos
import inno.jago.ast.converter.expression.toAddOperators
import inno.jago.ast.converter.expression.toMulOperators
import inno.jago.ast.model.expression.binary_expression.AddOperator
import inno.jago.ast.model.expression.binary_expression.MulOperator
import inno.jago.ast.model.statement.AddOpSimpleAssignOperatorNode
import inno.jago.ast.model.statement.MulOpSimpleAssignOperatorNode
import inno.jago.ast.model.statement.SimpleAssignOperatorNode
import inno.jago.common.EntityNotSupportedException

@Suppress("ReturnCount")
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
        return it.toEmptyStatementNode()
    }
    sendStmt()?.let {
        throw EntityNotSupportedException("SendStatement")
    }
    throw UnreachableCodeException()
}

fun GoParser.IncDecStmtContext.toIncDecStatementNode(): IncDecStatementNode {
    val incDec = PLUS_PLUS()?.let {
        IncDecStatementNode.IncDec.INC
    } ?: MINUS_MINUS()?.let {
        IncDecStatementNode.IncDec.DEC
    } ?: throw UnreachableCodeException()

    return IncDecStatementNode(
        pos = toPos(),
        expression = expression().toExpressionNode(),
        type = incDec
    )
}

fun GoParser.AssignmentContext.toAssignmentNode(): AssignmentNode = AssignmentNode(
    pos = toPos(),
    leftExpressions = expressionList()[0].expression().map { it.toExpressionNode() },
    rightExpressions = expressionList()[1].expression().map { it.toExpressionNode() },
    assignOperator = assign_op().toAssignOperatorNode()
)

fun GoParser.Assign_opContext.toAssignOperatorNode() = when {
    mul_op() != null -> MulOpSimpleAssignOperatorNode(MulOperator(toPos(), mul_op().toMulOperators()))
    add_op() != null -> AddOpSimpleAssignOperatorNode(AddOperator(toPos(), add_op().toAddOperators()))
    else -> SimpleAssignOperatorNode
}

fun GoParser.ShortVarDeclContext.toShortVarDeclNode(): ShortVarDeclNode = ShortVarDeclNode(
    pos = toPos(),
    identifierList = identifierList().IDENTIFIER().map { it.text },
    expression = expressionList().expression().map { it.toExpressionNode() }
)

fun GoParser.ExpressionStmtContext.toExpressionStatementNode(): ExpressionStatementNode = ExpressionStatementNode(
    pos = toPos(),
    expression = expression().toExpressionNode()
)

private fun GoParser.EmptyStmtContext.toEmptyStatementNode() = EmptyStatementNode(pos = toPos())
