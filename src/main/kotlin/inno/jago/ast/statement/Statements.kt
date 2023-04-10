package inno.jago.ast.statement

import inno.jago.ast.decl.DeclarationNode
import inno.jago.ast.expression.ExpressionNode
import inno.jago.entity.Entity
import inno.jago.lexer.Pos

sealed class StatementNode (
    pos: Pos
) : Entity(pos = pos)

class BlockStatementNode (
    pos: Pos,
    val block: List<StatementNode>
) : StatementNode(pos = pos)

class DeclarationStatementNode (
    pos: Pos,
    val declaration: DeclarationNode
) : StatementNode(pos = pos)

class ReturnStatementNode (
    pos: Pos,
    val expressions: List<ExpressionNode>
) : StatementNode(pos = pos)

class BreakStatementNode (
    pos: Pos
) : StatementNode(pos = pos)

class ContinueStatementNode (
    pos: Pos
) : StatementNode(pos = pos)
