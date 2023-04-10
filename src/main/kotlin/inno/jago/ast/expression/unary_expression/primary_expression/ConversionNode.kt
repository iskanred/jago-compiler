package inno.jago.ast.expression.unary_expression.primary_expression

import inno.jago.ast.expression.unary_expression.UnaryExpressionNode
import inno.jago.ast.expression.ExpressionNode
import inno.jago.ast.type.TypeNode
import inno.jago.lexer.Pos

open class ConversionNode(pos: Pos,
                          type: TypeNode,
                          expression: ExpressionNode?): PrimaryExpressionNode(pos, operatorNode, unaryExpressionNode)
