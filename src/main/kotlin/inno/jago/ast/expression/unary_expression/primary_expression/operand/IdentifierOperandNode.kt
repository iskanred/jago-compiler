package inno.jago.ast.expression.unary_expression.primary_expression.operand

import inno.jago.ast.ASTNode
import inno.jago.lexer.Pos

sealed class IdentifierOperandNode(pos: Pos) : ASTNode(pos)

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
) : ASTNode(pos = pos)
