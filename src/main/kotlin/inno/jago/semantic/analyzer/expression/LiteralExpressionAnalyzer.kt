package inno.jago.semantic.analyzer.expression

import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.literal_operand.BasicLiteralNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.literal_operand.BoolLiteralNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.literal_operand.CompositeLiteralNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.literal_operand.DoubleLiteralNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.literal_operand.FunctionLiteralNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.literal_operand.IntegerLiteralNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.literal_operand.LiteralNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.literal_operand.StringLiteralNode
import inno.jago.semantic.model.SemanticEntity

fun LiteralNode.toSemanticEntity(): SemanticEntity = when(this) {
    is BasicLiteralNode -> toSemanticEntity()
    is CompositeLiteralNode -> TODO()
    is FunctionLiteralNode -> TODO()
}

fun BasicLiteralNode.toSemanticEntity(): SemanticEntity = when(this) {
    is IntegerLiteralNode -> toSemanticEntity()
    is DoubleLiteralNode -> toSemanticEntity()
    is StringLiteralNode -> toSemanticEntity()
    is BoolLiteralNode -> toSemanticEntity()
}

fun IntegerLiteralNode.toSemanticEntity(): SemanticEntity {

}

fun DoubleLiteralNode.toSemanticEntity(): SemanticEntity {

}
fun StringLiteralNode.toSemanticEntity(): SemanticEntity {

}

fun BoolLiteralNode.toSemanticEntity(): SemanticEntity {

}
