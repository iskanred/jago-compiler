package inno.jago.ast.decl

import inno.jago.lexer.Pos

class DeclarationNode(
    pos: Pos,
    val identifier: String,
    val type: Type,
    val expression: Expression
) : TopLevelDeclNode(pos = pos)
