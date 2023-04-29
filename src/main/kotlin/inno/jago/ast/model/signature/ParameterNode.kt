package inno.jago.ast.model.signature

import inno.jago.ast.model.ASTNode
import inno.jago.ast.model.type.TypeNode
import inno.jago.lexer.Pos

class ParameterNode(
    pos: Pos,
    val identifier: String?,
    val type: TypeNode,
) : ASTNode(pos)
