package inno.jago.ast.expression.unary_expression.primary_expression.operand.literal_operand

import inno.jago.ast.expression.unary_expression.UnaryExpressionNode
import inno.jago.ast.expression.unary_expression.primary_expression.PrimaryExpressionNode
import inno.jago.ast.expression.unary_expression.primary_expression.operand.Operand
import inno.jago.lexer.Pos

class LiteralOperand(pos: Pos,
                     operatorNode: OperatorNode,
                     unaryExpressionNode: UnaryExpressionNode
) : Operand(pos, operatorNode, unaryExpressionNode)
