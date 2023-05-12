package inno.jago.cppgen.expression.unary_or_primary_expression

import inno.jago.ast.model.expression.unary_expression.UnaryExpressionNode
import inno.jago.ast.model.expression.unary_expression.UnaryOperatorNode
import inno.jago.ast.model.expression.unary_expression.UnaryOperators
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.LiteralOperandNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.literal_operand.CompositeLiteralNode
import inno.jago.ast.model.type.StructTypeNode
import inno.jago.ast.model.type.TypeNameNode
import inno.jago.ast.model.type.TypeNode
import inno.jago.common.EntityNotSupportedException

fun UnaryExpressionNode.translateToCode(): String =
    if (unaryOrPrimaryExpression is LiteralOperandNode &&
            unaryOrPrimaryExpression.literalNode is CompositeLiteralNode &&
            unaryOrPrimaryExpression.literalNode.literal is TypeNameNode
    ) {
        val expr = unaryOrPrimaryExpression.translateToCode()
        "${operator?.translateToCode(true) ?: ""}$expr"
    } else {
        val expr = unaryOrPrimaryExpression.translateToCode()
        val operator = operator?.translateToCode(false) ?: ""
        if (operator == "*") "($operator$expr)" else "$operator$expr"
    }

fun UnaryOperatorNode.translateToCode(isStruct: Boolean): String = when (this.operator) {
    UnaryOperators.PLUS -> "+"
    UnaryOperators.MINUS -> "-"
    UnaryOperators.EXCLAMATION -> "!"
    UnaryOperators.CARET -> "^"
    UnaryOperators.ASTERISK -> "*"
    UnaryOperators.AMPERSAND -> "new ".takeIf { isStruct } ?: "&"
    UnaryOperators.RECEIVE -> throw EntityNotSupportedException("RECEIVE")
}

