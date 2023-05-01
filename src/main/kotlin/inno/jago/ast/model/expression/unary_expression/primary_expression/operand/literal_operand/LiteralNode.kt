package inno.jago.ast.model.expression.unary_expression.primary_expression.operand.literal_operand

import inno.jago.ast.IncorrectNumberLiteral
import inno.jago.ast.model.signature.SignatureNode
import inno.jago.ast.model.statement.BlockStatementNode
import inno.jago.ast.model.type.ArrayTypeNode
import inno.jago.ast.model.ASTNode
import inno.jago.lexer.Pos

sealed class LiteralNode(pos: Pos) : ASTNode(pos = pos)

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
    val intValue: Int by lazy {
        try {
            if (value.lowercase().startsWith("0x")) {
                value.substring(2).toInt(radix = 16)
            } else if (value.startsWith("0")) {
                value.toInt(radix = 8)
            } else {
                value.toInt()
            }
        } catch(e: NumberFormatException) {
            throw IncorrectNumberLiteral(literal = value, pos = pos)
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
    val doubleValue: Double by lazy {
        try {
            value.toDouble()
        } catch (e: NumberFormatException) {
            throw IncorrectNumberLiteral(literal = value, pos = pos)
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
    val boolValue: Boolean by lazy {
        value.toBooleanStrict()
    }
}

class CompositeLiteralNode(
    pos: Pos,
    val literal: ArrayTypeNode,
    val literalValue: LiteralValueElementNode
) : LiteralNode(pos = pos)

class FunctionLiteralNode(
    pos: Pos,
    val signature: SignatureNode,
    val functionBody: BlockStatementNode
) : LiteralNode(pos = pos)
