package inno.jago.ast.expression.unary_expression

import inno.jago.ast.expression.ExpressionNode
import inno.jago.lexer.Pos
import inno.jago.ast.type.TypeNode

sealed class UnaryOrPrimaryExpression(
    pos: Pos
) : ExpressionNode(pos)

class UnaryExpressionNode(
    pos: Pos,
    val operatorNode: UnaryOperatorNode?,
    val unaryOrPrimaryExpressionNode: UnaryOrPrimaryExpression
) : UnaryOrPrimaryExpression(pos)

open class PrimaryExpressionNode(
    pos: Pos,
) : UnaryOrPrimaryExpression(pos)


// Primary expressions:
open class ConversionNode(
    pos: Pos,
    type: TypeNode,
    expression: ExpressionNode
): PrimaryExpressionNode(pos)

open class IndexExpressionNode(
    pos: Pos,
    primaryExpression: PrimaryExpressionNode,
    expression: ExpressionNode,
): PrimaryExpressionNode(pos)

open class MethodExpressionNode(
    pos: Pos,
    methodName: String,
    receiverType: TypeNode,
): PrimaryExpressionNode(pos)

open class SelectorExpressionNode(
    pos: Pos,
    primaryExpression: PrimaryExpressionNode,
    Selector: String,
): PrimaryExpressionNode(pos)


