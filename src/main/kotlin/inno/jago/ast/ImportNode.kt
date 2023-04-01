package inno.jago.ast

import inno.jago.entity.Entity
import inno.jago.lexer.Pos

class ImportNode(
    pos: Pos,
    val path: String,
    val alias: String,
    val importAll: Boolean
) : Entity(pos)