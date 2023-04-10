package inno.jago.ast.expression.unary_expression.primary_expression.operand

import inno.jago.ast.expression.unary_expression.PrimaryExpressionNode
import inno.jago.ast.expression.unary_expression.primary_expression.operand.literal_operand.LiteralNode
import inno.jago.lexer.Pos

sealed class Operand(
    pos: Pos,
) : PrimaryExpressionNode(pos)

class LiteralOperand(
    pos: Pos,
    val literalNode: LiteralNode
) : Operand(pos)
