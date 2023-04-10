package inno.jago.ast.expression.unary_expression.primary_expression.operand.literal_operand

import inno.jago.ast.signature.SignatureNode
import inno.jago.ast.statement.StatementNode
import inno.jago.ast.type.TypeNode

sealed class LiteralNode

class BasicLiteralNode(
    val value: String,
    val type: TypeNode
) : LiteralNode()

class CompositeLiteralNode(
    val literal: LiteralTypeNode,
    val literalValue: List<Element>
) : LiteralNode()

class FunctionLiteralNode(
    val signature: SignatureNode,
    val functionBody: List<StatementNode>
) : LiteralNode()
