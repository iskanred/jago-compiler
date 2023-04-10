package inno.jago.ast.expression.unary_expression.primary_expression.operand.literal_operand

import inno.jago.ast.type.TypeNode
import inno.jago.entity.Entity
import inno.jago.lexer.Pos

sealed class LiteralTypeNode(
    pos: Pos
) : Entity(pos)

class ArrayTypeNodeNode(
    pos: Pos,
    val length: Int,
    val type: TypeNode
) : LiteralTypeNode(pos)

