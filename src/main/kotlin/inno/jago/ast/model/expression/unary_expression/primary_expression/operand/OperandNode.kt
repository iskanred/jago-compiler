package inno.jago.ast.model.expression.unary_expression.primary_expression.operand

import inno.jago.ast.model.expression.ExpressionNode
import inno.jago.ast.model.expression.unary_expression.PrimaryExpressionNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.literal_operand.LiteralNode
import inno.jago.lexer.Pos

sealed class OperandNode(
    pos: Pos,
) : PrimaryExpressionNode(pos)

class LiteralOperandNode(
    pos: Pos,
    val literalNode: LiteralNode
) : OperandNode(pos)

class OperandNameNode(
    pos: Pos,
    val name: IdentifierOperandNode
): OperandNode(pos)

class ExpressionOperandNode(
    pos: Pos,
    val expression: ExpressionNode
): OperandNode(pos)
