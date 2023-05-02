package inno.jago.semantic.analyzer.expression

import inno.jago.ast.model.expression.ExpressionNode
import inno.jago.ast.model.expression.binary_expression.BinaryExpression
import inno.jago.ast.model.expression.unary_expression.UnaryOrPrimaryExpression
import inno.jago.common.UnreachableCodeException
import inno.jago.semantic.model.ExpressionEntity
import inno.jago.semantic.model.ScopeNode

fun ExpressionNode.toSemanticEntity(scope: ScopeNode): ExpressionEntity = when (this) {
    is UnaryOrPrimaryExpression -> toSemanticEntity(scope)
    is BinaryExpression -> toSemanticEntity(scope)
    else -> throw UnreachableCodeException()
}


