package inno.jago.ast

import inno.jago.entity.Entity
import inno.jago.lexer.Pos

class PackageNode(
    pos: Pos,
    val name: String
) : Entity(pos = pos)
