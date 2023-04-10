package inno.jago.ast.expression.binary_expression

import inno.jago.lexer.Pos
import inno.jago.ast.expression.ExpressionNode

open class BinaryExpression(
    pos: Pos,
    val binaryOperator: BinaryOperator,
    val leftExpression: ExpressionNode,
    val rightExpression: ExpressionNode
) : ExpressionNode(pos)


sealed class BinaryOperator(
    pos: Pos
) : ExpressionNode(pos)

open class RelationOperator(
    pos: Pos,
    val operator: RelationOperators,
) : BinaryOperator(pos)

open class AddOperator(
    pos: Pos,
    val operator: AddOperators,
) : BinaryOperator(pos)

open class MulOperator(
    pos: Pos,
    val operator: MulOperators,
) : BinaryOperator(pos)

open class LogicOperator(
    pos: Pos,
    val operator: LogicOperators,
) : BinaryOperator(pos)


enum class LogicOperators {
    LOGIC_OR, LOGIC_AND
}
enum class RelationOperators {
    EQUALS, NOT_EQUALS, LESS, LESS_OR_EQUALS, GREATER, GREATER_OR_EQUALS,
}
enum class AddOperators {
    PLUS, MINUS, OR, CARET,
}
enum class MulOperators {
    ASTERISK, DIV, MOD, LSHIFT, RSHIFT, AMPERSAND, BIT_CLEAR
}
