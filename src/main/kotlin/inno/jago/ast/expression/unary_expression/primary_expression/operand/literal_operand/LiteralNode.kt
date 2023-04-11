package inno.jago.ast.expression.unary_expression.primary_expression.operand.literal_operand

import inno.jago.ast.signature.SignatureNode
import inno.jago.ast.statement.BlockStatementNode
import inno.jago.ast.type.ArrayTypeNode
import inno.jago.entity.Entity
import inno.jago.lexer.Pos

sealed class LiteralNode(pos: Pos) : Entity(pos = pos)

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
)

class DoubleLiteralNode(
    pos: Pos,
    value: String
) : BasicLiteralNode(
    pos = pos,
    value = value
)

class StringLiteralNode(
    pos: Pos,
    value: String
) : BasicLiteralNode(
    pos = pos,
    value = value
)

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
