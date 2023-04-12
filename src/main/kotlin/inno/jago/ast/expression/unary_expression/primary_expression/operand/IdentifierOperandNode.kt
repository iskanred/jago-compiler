package inno.jago.ast.expression.unary_expression.primary_expression.operand

import inno.jago.entity.Entity
import inno.jago.lexer.Pos

sealed class IdentifierOperandNode(pos: Pos) : Entity(pos)

class SimpleIdentifierOperandNode(
    pos: Pos,
    val identifier: String
) : IdentifierOperandNode(pos)

class QualifiedIdentifierOperandNode(
    pos: Pos,
    val qualifiedIdentifier: QualifiedIdentifierNode
) : IdentifierOperandNode(pos)

class QualifiedIdentifierNode(
    pos: Pos,
    val packageName: String,
    val identifier: String
) : Entity(pos = pos)
