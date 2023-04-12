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
    name = typeName
) {
    companion object {
        const val typeName = "int"
    }
}

class DoubleTypeNode(
    pos: Pos,
) : BasicTypeNode(
    pos = pos,
    name = typeName
) {
    companion object {
        const val typeName = "float64"
    }
}

class StringTypeNode(
    pos: Pos,
) : BasicTypeNode(
    pos = pos,
    name = typeName
) {
    companion object {
        const val typeName = "string"
    }
}

class BoolTypeNode(
    pos: Pos,
) : BasicTypeNode(
    pos = pos,
    name = typeName
) {
    companion object {
        const val typeName = "bool"
    }
}
