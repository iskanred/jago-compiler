package inno.jago.ast.type

import inno.jago.lexer.Pos

sealed class BasicTypeNode(
    pos: Pos,
    name: String
) : TypeNode(
    pos = pos,
    name = name
)

class IntegerTypeNode(
    pos: Pos,
) : BasicTypeNode(
    pos = pos,
    name = "int"
)

class DoubleTypeNode(
    pos: Pos,
) : BasicTypeNode(
    pos = pos,
    name = "float64"
)

class StringTypeNode<T>(
    pos: Pos,
) : BasicTypeNode(
    pos = pos,
    name = "string"
)
