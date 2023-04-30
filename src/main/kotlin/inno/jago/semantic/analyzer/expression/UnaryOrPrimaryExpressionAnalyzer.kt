package inno.jago.semantic.analyzer.expression

import inno.jago.ast.model.expression.unary_expression.ApplicationExpressionNode
import inno.jago.ast.model.expression.unary_expression.ConversionNode
import inno.jago.ast.model.expression.unary_expression.IndexExpressionNode
import inno.jago.ast.model.expression.unary_expression.PrimaryExpressionNode
import inno.jago.ast.model.expression.unary_expression.SelectorExpressionNode
import inno.jago.ast.model.expression.unary_expression.UnaryExpressionNode
import inno.jago.ast.model.expression.unary_expression.UnaryOrPrimaryExpression
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.ExpressionOperandNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.LiteralOperandNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.OperandNameNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.OperandNode
import inno.jago.exception.UnreachableCodeException
import inno.jago.semantic.NonCastableTypeException
import inno.jago.semantic.model.EntityType
import inno.jago.semantic.model.ScopeNode
import inno.jago.semantic.model.SemanticEntity
import inno.jago.semantic.model.Type
import inno.jago.semantic.model.toType

fun UnaryOrPrimaryExpression.toSemanticEntity(scope: ScopeNode): SemanticEntity = when (this) {
    is UnaryExpressionNode -> toSemanticEntity(scope)
    is PrimaryExpressionNode -> toSemanticEntity(scope)
}

fun UnaryExpressionNode.toSemanticEntity(scope: ScopeNode): SemanticEntity {
    TODO()
}

@Suppress("CyclomaticComplexMethod")
fun PrimaryExpressionNode.toSemanticEntity(scope: ScopeNode): SemanticEntity = when (this) {
    is ExpressionOperandNode -> TODO()
    is ConversionNode -> toSemanticEntity(scope)
    is IndexExpressionNode -> TODO()
    is SelectorExpressionNode -> TODO()
    is ApplicationExpressionNode -> TODO()
    // делает iskanred
    is LiteralOperandNode -> toSemanticEntity(scope)
    is OperandNameNode -> toSemanticEntity(scope)
    is OperandNode -> TODO()
    else -> throw UnreachableCodeException()
}

fun ConversionNode.toSemanticEntity(scope: ScopeNode): SemanticEntity {
    var entity = expression.toSemanticEntity(scope)
    if (entity.type != type.toType() ) {
        if (entity.type !is Type.NumberType || type.toType() !is Type.NumberType) {
            throw NonCastableTypeException(entity.type, type.toType(), pos)
        }
        return SemanticEntity(
            type = type.toType(),
            pos = pos,
            entityType = EntityType.EXPRESSION
        )
    }
    return entity
}
