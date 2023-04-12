package inno.jago.ast.global

import inno.jago.ast.ASTNode
import inno.jago.lexer.Pos

class PackageNode(
    pos: Pos,
    val name: String
) : ASTNode(pos = pos)
