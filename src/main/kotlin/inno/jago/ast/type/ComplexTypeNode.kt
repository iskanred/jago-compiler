package inno.jago.ast.type

import inno.jago.ast.expression.ExpressionNode
import inno.jago.lexer.Pos

sealed class ComplexTypeNode(
    pos: Pos,
    name: String
) : TypeNode(
    pos = pos,
    name = name
)

class ArrayType(
    pos: Pos,
    val length: ExpressionNode,
    val elementType: TypeNode
) : ComplexTypeNode(
    pos = pos,
    name = "array<${elementType.name}>"
)
