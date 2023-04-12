package inno.jago.ast.expression.unary_expression

import inno.jago.ast.expression.ExpressionNode
import inno.jago.lexer.Pos
import inno.jago.ast.type.TypeNode
import inno.jago.entity.Entity

sealed class UnaryOrPrimaryExpression(
    pos: Pos
) : ExpressionNode(pos)

class UnaryExpressionNode(
    pos: Pos,
    val operator: UnaryOperatorNode?,
    val unaryOrPrimaryExpression: UnaryOrPrimaryExpression
) : UnaryOrPrimaryExpression(pos)

open class PrimaryExpressionNode(
    pos: Pos,
) : UnaryOrPrimaryExpression(pos)


// Primary expressions:
open class ConversionNode(
    pos: Pos,
    val type: TypeNode,
    val expression: ExpressionNode
) : PrimaryExpressionNode(pos)

open class IndexExpressionNode(
    pos: Pos,
    val primaryExpression: PrimaryExpressionNode,
    val expression: ExpressionNode,
) : PrimaryExpressionNode(pos)

open class MethodExpressionNode(
    pos: Pos,
    val methodName: String,
    val receiverType: TypeNode,
) : PrimaryExpressionNode(pos)

open class SelectorExpressionNode(
    pos: Pos,
    val primaryExpression: PrimaryExpressionNode,
    val selector: String,
) : PrimaryExpressionNode(pos)


open class UnaryOperatorNode(
    pos: Pos,
    val operator: UnaryOperators
) : Entity(pos)


class ApplicationExpressionNode(
    pos: Pos,
    val leftExpression: PrimaryExpressionNode,
    val expressions: List<ExpressionNode>
) : PrimaryExpressionNode(pos = pos)

enum class UnaryOperators {
    PLUS, MINUS, EXCLAMATION, CARET, ASTERISK, AMPERSAND, RECEIVE
}

