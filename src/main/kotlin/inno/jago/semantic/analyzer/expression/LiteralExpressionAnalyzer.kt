package inno.jago.semantic.analyzer.expression

import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.literal_operand.BasicLiteralNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.literal_operand.BoolLiteralNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.literal_operand.CompositeLiteralNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.literal_operand.DoubleLiteralNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.literal_operand.FunctionLiteralNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.literal_operand.IntegerLiteralNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.literal_operand.LiteralNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.literal_operand.StringLiteralNode
import inno.jago.semantic.model.EntityType
import inno.jago.semantic.model.SemanticEntity
import inno.jago.semantic.model.Type

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

fun IntegerLiteralNode.toSemanticEntity() = SemanticEntity(
    type = Type.IntegerType,
    pos = pos,
    entityType = EntityType.NO_IDENTIFIER
)

fun DoubleLiteralNode.toSemanticEntity() = SemanticEntity(
    type = Type.DoubleType,
    pos = pos,
    entityType = EntityType.NO_IDENTIFIER
)

fun StringLiteralNode.toSemanticEntity() = SemanticEntity(
    type = Type.StringType,
    pos = pos,
    entityType = EntityType.NO_IDENTIFIER
)

fun BoolLiteralNode.toSemanticEntity() = SemanticEntity(
    type = Type.BoolType,
    pos = pos,
    entityType = EntityType.NO_IDENTIFIER
)
