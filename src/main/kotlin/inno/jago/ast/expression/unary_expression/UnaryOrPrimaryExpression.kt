package inno.jago.ast.expression.unary_expression

import inno.jago.ast.expression.ExpressionNode
import inno.jago.lexer.Pos

sealed class UnaryOrPrimaryExpression(
    pos: Pos
) : ExpressionNode(pos) {
    open class UnaryExpressionNode(pos: Pos,
                                   val operatorNode: UnaryOperatorNode?,
                                   val unaryOrPrimaryExpressionNode: UnaryOrPrimaryExpression
    ): ExpressionNode(pos)
    class PrimaryExpression();
}
