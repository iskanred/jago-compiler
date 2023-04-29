package inno.jago.ast.model.global

import inno.jago.ast.model.ASTNode
import inno.jago.lexer.Pos

class ImportNode(
    pos: Pos,
    val path: String,
    val alias: String?,
    val importAll: Boolean
) : ASTNode(pos = pos) {
    val name = path.split("/").last()
}
