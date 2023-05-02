package inno.jago.ast.model.type

import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.literal_operand.IntegerLiteralNode
import inno.jago.ast.model.signature.SignatureNode
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
    val length: IntegerLiteralNode,
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
    val signature: SignatureNode
) : ComplexTypeNode(
    pos = pos,
    name = "function type"
)
