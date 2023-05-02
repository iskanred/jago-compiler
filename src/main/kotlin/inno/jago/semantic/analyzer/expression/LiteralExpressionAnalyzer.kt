package inno.jago.semantic.analyzer.expression

import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.literal_operand.BasicLiteralNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.literal_operand.BoolLiteralNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.literal_operand.CompositeLiteralNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.literal_operand.DoubleLiteralNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.literal_operand.FunctionLiteralNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.literal_operand.IntegerLiteralNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.literal_operand.LiteralNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.literal_operand.StringLiteralNode
import inno.jago.semantic.model.ExpressionEntity
import inno.jago.semantic.model.Type

fun LiteralNode.toSemanticEntity(): ExpressionEntity = when(this) {
    is BasicLiteralNode -> toSemanticEntity()
    is CompositeLiteralNode -> TODO()
    is FunctionLiteralNode -> TODO()
}

fun BasicLiteralNode.toSemanticEntity(): ExpressionEntity = when(this) {
    is IntegerLiteralNode -> toSemanticEntity()
    is DoubleLiteralNode -> toSemanticEntity()
    is StringLiteralNode -> toSemanticEntity()
    is BoolLiteralNode -> toSemanticEntity()
}

fun IntegerLiteralNode.toSemanticEntity() = ExpressionEntity(type = Type.IntegerType)

fun DoubleLiteralNode.toSemanticEntity() = ExpressionEntity(type = Type.DoubleType)

fun StringLiteralNode.toSemanticEntity() = ExpressionEntity(type = Type.StringType)

fun BoolLiteralNode.toSemanticEntity() = ExpressionEntity(type = Type.BoolType)
