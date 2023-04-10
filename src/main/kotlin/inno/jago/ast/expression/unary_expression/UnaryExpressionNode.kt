package inno.jago.ast.expression.unary_expression

import inno.jago.ast.expression.ExpressionNode
import inno.jago.lexer.Pos
import java.util.function.UnaryOperator

open class UnaryExpressionNode(pos: Pos,
                               val operatorNode: UnaryOperatorNode?,
                               val unaryExpressionNode: UnaryExpressionNode
): ExpressionNode(pos)
