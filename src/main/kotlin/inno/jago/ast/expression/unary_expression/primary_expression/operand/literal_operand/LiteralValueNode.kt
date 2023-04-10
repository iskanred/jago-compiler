package inno.jago.ast.expression.unary_expression.primary_expression.operand.literal_operand

import inno.jago.ast.expression.ExpressionNode
import inno.jago.entity.Entity
import inno.jago.lexer.Pos

sealed class LiteralValueNode(
    pos: Pos,
    val elements: List<ElementNode>
) : Entity(pos = pos)

sealed class ElementNode(
    pos: Pos,
    elements: List<ElementNode>
) : LiteralValueNode(pos = pos, elements = elements)

class ExpressionElement(
    pos: Pos,
    elements: List<ElementNode>,
    val expression: ExpressionNode
) : ElementNode(pos = pos, elements = elements)

class LiteralValueElement(
    pos: Pos,
    elements: List<ElementNode>,
    val literalValue: LiteralValueNode
) : ElementNode(pos = pos, elements = elements )
