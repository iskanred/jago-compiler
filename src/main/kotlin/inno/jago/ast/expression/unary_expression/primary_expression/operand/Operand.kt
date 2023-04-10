package inno.jago.ast.expression.unary_expression.primary_expression.operand

import inno.jago.ast.expression.unary_expression.PrimaryExpressionNode
import inno.jago.lexer.Pos

open class Operand(
    pos: Pos,
) : PrimaryExpressionNode(pos)
