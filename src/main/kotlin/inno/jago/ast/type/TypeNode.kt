package inno.jago.ast.type

import inno.jago.ast.ASTNode
import inno.jago.lexer.Pos

sealed class TypeNode(
    pos: Pos,
    val name: String
) : ASTNode(pos = pos)
