package inno.jago.ast.expression.unary_expression.primary_expression

import inno.jago.ast.expression.unary_expression.UnaryExpressionNode
import inno.jago.ast.expression.ExpressionNode
import inno.jago.lexer.Pos

open class ConversionNode(pos: Pos,
                          type: Type,
                          expression: ExpressionNode?): PrimaryExpressionNode(pos, operatorNode, unaryExpressionNode)
