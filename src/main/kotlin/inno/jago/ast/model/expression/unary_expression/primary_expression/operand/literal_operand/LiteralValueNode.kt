package inno.jago.ast.model.expression.unary_expression.primary_expression.operand.literal_operand

import inno.jago.ast.model.expression.ExpressionNode
import inno.jago.ast.model.ASTNode
import inno.jago.lexer.Pos

sealed class ElementNode(
    pos: Pos,
) : ASTNode(pos)

class ExpressionElementNode(
    pos: Pos,
    val expression: ExpressionNode
) : ElementNode(pos = pos)

class LiteralValueElementNode(
    pos: Pos,
    val elements: List<ElementNode>
) : ElementNode(pos = pos)
