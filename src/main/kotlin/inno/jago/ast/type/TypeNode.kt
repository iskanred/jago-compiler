package inno.jago.ast.type

import inno.jago.entity.Entity
import inno.jago.lexer.Pos

sealed class TypeNode(
    pos: Pos,
    val name: String
) : Entity(pos = pos)
