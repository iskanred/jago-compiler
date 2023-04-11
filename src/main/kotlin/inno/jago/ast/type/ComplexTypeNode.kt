package inno.jago.ast.type

import inno.jago.ast.expression.ExpressionNode
import inno.jago.ast.signature.SignatureNode
import inno.jago.lexer.Pos

sealed class ComplexTypeNode(
    pos: Pos,
    name: String
) : TypeNode(
    pos = pos,
    name = name
)

class ArrayTypeNode(
    pos: Pos,
    val length: ExpressionNode,
    val elementType: TypeNode
) : ComplexTypeNode(
    pos = pos,
    name = "array<${elementType.name}>"
)

class PointerTypeNode(
    pos: Pos,
    val baseType: TypeNode
) : ComplexTypeNode(
    pos = pos,
    name = "pointer to ${baseType.name}"
)

class FunctionTypeNode(
    pos: Pos,
    val elementType: SignatureNode
) : ComplexTypeNode(
    pos = pos,
    name = "function type"
)
