package inno.jago.ast.expression.unary_expression.primary_expression.operand.literal_operand

import inno.jago.ast.signature.SignatureNode
import inno.jago.ast.statement.StatementNode
import inno.jago.ast.type.TypeNode
import inno.jago.entity.Entity
import inno.jago.lexer.Pos

sealed class LiteralNode(pos: Pos) : Entity(pos = pos)

class BasicLiteralNode(
    pos: Pos,
    val value: String,
    val type: TypeNode
) : LiteralNode(pos = pos)

class CompositeLiteralNode(
    pos: Pos,
    val literal: LiteralTypeNode,
    val literalValue: LiteralValue
) : LiteralNode(pos = pos)

class FunctionLiteralNode(
    pos: Pos,
    val signature: SignatureNode,
    val functionBody: List<StatementNode>
) : LiteralNode(pos = pos)
