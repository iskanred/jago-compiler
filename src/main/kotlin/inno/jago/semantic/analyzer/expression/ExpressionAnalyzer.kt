package inno.jago.semantic.analyzer.expression

import inno.jago.ast.model.expression.ExpressionNode
import inno.jago.ast.model.expression.binary_expression.BinaryExpression
import inno.jago.ast.model.expression.unary_expression.UnaryOrPrimaryExpression
import inno.jago.exception.UnreachableCodeException
import inno.jago.semantic.model.ScopeNode
import inno.jago.semantic.model.SemanticEntity

fun ExpressionNode.toSemanticEntity(scope: ScopeNode): SemanticEntity = when(this) {
    is UnaryOrPrimaryExpression -> TODO()
    is BinaryExpression -> TODO()
    else -> throw UnreachableCodeException()
}

fun UnaryOrPrimaryExpression.toSemanticEntity(scope: ScopeNode): SemanticEntity = when(this) {

}

fun BinaryExpression.toSemanticEntity(scope: ScopeNode): SemanticEntity = when(this) {

}
