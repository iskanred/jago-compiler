package inno.jago.ast.statement

import inno.jago.ast.expression.ExpressionNode
import inno.jago.lexer.Pos

sealed class ForStatementNode(
    pos: Pos,
    var block: BlockStatementNode
) : StatementNode(pos = pos)

class ConditionalForStatementNode(
    pos: Pos,
    block: BlockStatementNode,
    var condition: ExpressionNode
) : ForStatementNode(pos = pos, block = block)

class ForClauseStatementNode(
    pos: Pos,
    block: BlockStatementNode,
    var initStatementNode: BlockStatementNode?,
    var condition: ExpressionNode?,
    var postStatementNode: SimpleStatementNode?
) : ForStatementNode(pos = pos, block = block)
