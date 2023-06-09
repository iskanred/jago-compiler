package inno.jago.ast.model.expression.unary_expression

import inno.jago.ast.model.expression.ExpressionNode
import inno.jago.lexer.Pos
import inno.jago.ast.model.type.TypeNode
import inno.jago.ast.model.ASTNode

sealed class UnaryOrPrimaryExpressionNode(
    pos: Pos
) : ExpressionNode(pos)

class UnaryExpressionNode(
    pos: Pos,
    val operator: UnaryOperatorNode?,
    val unaryOrPrimaryExpression: UnaryOrPrimaryExpressionNode
) : UnaryOrPrimaryExpressionNode(pos)

open class PrimaryExpressionNode(
    pos: Pos,
) : UnaryOrPrimaryExpressionNode(pos)


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

open class SelectorExpressionNode(
    pos: Pos,
    val primaryExpression: PrimaryExpressionNode,
    val selector: String,
) : PrimaryExpressionNode(pos)

open class UnaryOperatorNode(
    pos: Pos,
    val operator: UnaryOperators
) : ASTNode(pos)

class ApplicationExpressionNode(
    pos: Pos,
    val leftExpression: PrimaryExpressionNode,
    val expressions: List<ExpressionNode>
) : PrimaryExpressionNode(pos = pos)

enum class UnaryOperators {
    PLUS, MINUS, EXCLAMATION, CARET, ASTERISK, AMPERSAND, RECEIVE
}

