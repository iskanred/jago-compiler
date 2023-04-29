package inno.jago.ast.model.type

import inno.jago.common.BOOL_TYPE_NAME
import inno.jago.common.DOUBLE_TYPE_NAME
import inno.jago.common.INT_TYPE_NAME
import inno.jago.common.STRING_TYPE_NAME
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
    name = INT_TYPE_NAME
)

class DoubleTypeNode(
    pos: Pos,
) : BasicTypeNode(
    pos = pos,
    name = DOUBLE_TYPE_NAME
)

class StringTypeNode(
    pos: Pos,
) : BasicTypeNode(
    pos = pos,
    name = STRING_TYPE_NAME
)

class BoolTypeNode(
    pos: Pos,
) : BasicTypeNode(
    pos = pos,
    name = BOOL_TYPE_NAME
)
