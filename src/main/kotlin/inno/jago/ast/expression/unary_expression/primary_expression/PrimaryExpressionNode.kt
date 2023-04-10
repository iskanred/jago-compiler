package inno.jago.ast.expression.unary_expression.primary_expression

import inno.jago.ast.expression.unary_expression.UnaryExpressionNode
import inno.jago.lexer.Pos

open class PrimaryExpressionNode(pos: Pos,
                                 operatorNode: UnaryOperatorNode?,
                                 unaryExpressionNode: UnaryExpressionNode): UnaryExpressionNode(pos, operatorNode, unaryExpressionNode)
