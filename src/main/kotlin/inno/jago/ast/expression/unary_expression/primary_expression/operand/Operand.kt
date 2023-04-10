package inno.jago.ast.expression.unary_expression.primary_expression.operand

import inno.jago.ast.expression.unary_expression.UnaryExpressionNode
import inno.jago.ast.expression.unary_expression.primary_expression.PrimaryExpressionNode
import inno.jago.lexer.Pos

open class Operand(pos: Pos,
                   operatorNode: OperatorNode,
                   unaryExpressionNode: UnaryExpressionNode
) : PrimaryExpressionNode(pos, operatorNode, unaryExpressionNode)
