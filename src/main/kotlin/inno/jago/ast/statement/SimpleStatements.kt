package inno.jago.ast.statement

import inno.jago.lexer.Pos

sealed class SimpleStatementNode (
    pos: Pos
) : StatementNode(pos = pos)

