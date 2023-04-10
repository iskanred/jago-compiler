package inno.jago.ast.expression.unary_expression.primary_expression.operand.literal_operand

import inno.jago.ast.expression.unary_expression.primary_expression.operand.Operand
import inno.jago.ast.signature.SignatureNode
import inno.jago.ast.type.TypeNode
import inno.jago.lexer.Pos

class LiteralOperand(
    pos: Pos,
    val literalNode: LiteralNode
) : Operand(pos)


