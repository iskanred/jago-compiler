package inno.jago.ast.expression.unary_expression.primary_expression.operand

import inno.jago.ast.expression.ExpressionNode
import inno.jago.ast.expression.unary_expression.PrimaryExpressionNode
import inno.jago.ast.expression.unary_expression.primary_expression.operand.literal_operand.LiteralNode
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
    val identifier: OperandIdentifierNode
): OperandNode(pos)

class ExpressionOperandNode(
    pos: Pos,
    val expressionNode: ExpressionNode
): OperandNode(pos)
