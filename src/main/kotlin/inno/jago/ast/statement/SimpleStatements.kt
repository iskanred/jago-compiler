package inno.jago.ast.statement

import inno.jago.ast.expression.ExpressionNode
import inno.jago.lexer.Pos

sealed class SimpleStatementNode (
    pos: Pos
) : StatementNode(pos = pos)

class IncDecStatementNode (
    pos: Pos,
    val expression: ExpressionNode,
    val type: IncDec
) : SimpleStatementNode(pos = pos)

enum class IncDec {
    INC,
    DEC
}

class AssignmentNode (
    pos: Pos,
    val left: ExpressionNode,
    val right: ExpressionNode,
) : SimpleStatementNode(pos = pos)

class ShortVarDeclNode (
    pos: Pos,
    val left: ExpressionNode,
    val right: ExpressionNode,
) : SimpleStatementNode(pos = pos)

class ExpressionStatementNode (
    pos: Pos,
    val expression: ExpressionNode
) : SimpleStatementNode(pos = pos)
