package inno.jago.ast.model.expression.unary_expression.primary_expression.operand.literal_operand

import inno.jago.ast.IncorrectNumberLiteralException
import inno.jago.ast.model.signature.SignatureNode
import inno.jago.ast.model.statement.BlockStatementNode
import inno.jago.ast.model.expression.ExpressionNode
import inno.jago.ast.model.type.ComplexTypeNode
import inno.jago.lexer.Pos

sealed class LiteralNode(pos: Pos) : ExpressionNode(pos = pos)

sealed class BasicLiteralNode(
    pos: Pos,
    val value: String,
) : LiteralNode(pos = pos)

class IntegerLiteralNode(
    pos: Pos,
    value: String
) : BasicLiteralNode(
    pos = pos,
    value = value
) {
    val intValue = run {
        try {
            if (value.lowercase().startsWith("0x")) {
                value.substring(2).toInt(radix = 16)
            } else if (value.startsWith("0")) {
                value.toInt(radix = 8)
            } else {
                value.toInt()
            }
        } catch(e: NumberFormatException) {
            throw IncorrectNumberLiteralException(literal = value, pos = pos)
        }
    }
}

class DoubleLiteralNode(
    pos: Pos,
    value: String
) : BasicLiteralNode(
    pos = pos,
    value = value
) {
    val doubleValue: Double = run {
        try {
            value.toDouble()
        } catch (e: NumberFormatException) {
            throw IncorrectNumberLiteralException(literal = value, pos = pos)
        }
    }
}

class StringLiteralNode(
    pos: Pos,
    value: String
) : BasicLiteralNode(
    pos = pos,
    value = value
)

class BoolLiteralNode(
    pos: Pos,
    value: String
) : BasicLiteralNode(
    pos = pos,
    value = value
) {
    val boolValue: Boolean = run {
        value.toBooleanStrict()
    }
}

class CompositeLiteralNode(
    pos: Pos,
    val literal: ComplexTypeNode,
    val literalValue: LiteralValueElementNode
) : LiteralNode(pos = pos)


class FunctionLiteralNode(
    pos: Pos,
    val signature: SignatureNode,
    val functionBody: BlockStatementNode
) : LiteralNode(pos = pos)
