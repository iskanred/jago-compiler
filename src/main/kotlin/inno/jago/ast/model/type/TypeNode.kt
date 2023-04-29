package inno.jago.ast.model.type

import inno.jago.ast.model.ASTNode
import inno.jago.lexer.Pos

sealed class TypeNode(
    pos: Pos,
    val name: String
) : ASTNode(pos = pos)
