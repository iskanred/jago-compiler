package inno.jago.ast.expression.unary_expression.primary_expression.operand

import inno.jago.entity.Entity
import inno.jago.lexer.Pos

sealed class OperandIdentifierNode(pos: Pos) : Entity(pos)

class SimpleOperandIdentifierNode(
    pos: Pos,
    val identifier: String
) : OperandIdentifierNode(pos)

class QualifiedIdentifierNode(
    pos: Pos,
    val packageName: String,
    val identifier: String
) : OperandIdentifierNode(pos)
