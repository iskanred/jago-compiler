package inno.jago.ast.model.statement

import inno.jago.ast.model.decl.DeclarationNode
import inno.jago.ast.model.expression.ExpressionNode
import inno.jago.ast.model.ASTNode
import inno.jago.lexer.Pos

sealed class StatementNode (
    pos: Pos
) : ASTNode(pos = pos)

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
