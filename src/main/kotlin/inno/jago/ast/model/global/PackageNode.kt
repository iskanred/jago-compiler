package inno.jago.ast.model.global

import inno.jago.ast.model.ASTNode
import inno.jago.lexer.Pos

class PackageNode(
    pos: Pos,
    val name: String
) : ASTNode(pos = pos)
