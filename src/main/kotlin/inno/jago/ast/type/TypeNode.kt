package inno.jago.ast.type


import inno.jago.lexer.Pos

sealed class TypeNode(
    pos: Pos,
    val typeName: String
): Entity(pos = pos)
