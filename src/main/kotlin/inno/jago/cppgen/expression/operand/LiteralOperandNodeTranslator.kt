package inno.jago.cppgen.expression.operand

import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.LiteralOperandNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.literal_operand.BasicLiteralNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.literal_operand.BoolLiteralNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.literal_operand.CompositeLiteralNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.literal_operand.DoubleLiteralNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.literal_operand.FunctionLiteralNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.literal_operand.IntegerLiteralNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.literal_operand.StringLiteralNode
import inno.jago.cppgen.expression.translateToCode
import inno.jago.cppgen.type.translateToCode


fun LiteralOperandNode.translateToCode(): String {
    when (val it = literalNode) {
        is IntegerLiteralNode -> {
            return it.intValue.toString()
        }
        is BoolLiteralNode -> {
            return it.boolValue.toString()
        }
        is DoubleLiteralNode -> {
            return it.doubleValue.toString()
        }
        is StringLiteralNode -> {
            return "\"" + it.toString() + "\""
        }
        is CompositeLiteralNode -> {
            return it.literal.elementType.translateToCode() + "[" + it.literal.length.translateToCode() + "]{" + it.literalValue.elements.map { elem -> elem.translateToCode() } + "}";
        }
        is FunctionLiteralNode -> TODO()
    }
}
