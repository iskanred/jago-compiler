package inno.jago.ast.statement

import inno.jago.ast.expression.ExpressionNode
import inno.jago.lexer.Pos

class IfStatementNode(
    pos: Pos,
    var simpleStatement: SimpleStatementNode?,
    var expression: ExpressionNode,
    var block: BlockStatementNode,
    var elseBranch: ElseStatementNode?
) : StatementNode(pos = pos)

sealed class ElseStatementNode (
    pos: Pos
) : StatementNode(pos = pos)

class ElseIfStatementNode (
    pos: Pos,
    var ifStmt: IfStatementNode
) : ElseStatementNode(pos = pos)

class SimpleElseStatementNode (
    pos: Pos,
    val block: BlockStatementNode
) : ElseStatementNode(pos = pos)
