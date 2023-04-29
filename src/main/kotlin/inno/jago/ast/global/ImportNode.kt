package inno.jago.ast.global

import inno.jago.ast.ASTNode
import inno.jago.lexer.Pos

class ImportNode(
    pos: Pos,
    val path: String,
    val alias: String?,
    val importAll: Boolean
) : ASTNode(pos = pos) {
    val name = path.split("/").last()
}
