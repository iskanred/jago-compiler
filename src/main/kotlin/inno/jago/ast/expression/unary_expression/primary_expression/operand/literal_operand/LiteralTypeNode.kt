package inno.jago.ast.expression.unary_expression.primary_expression.operand.literal_operand

import inno.jago.ast.type.TypeNode

sealed class LiteralTypeNode()

class ArrayTypeNodeNode(
    val length: Int,
    val type: TypeNode
) : LiteralTypeNode()

