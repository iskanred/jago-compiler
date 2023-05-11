@file:Suppress("PackageNaming")
package inno.jago.cppgen.expression.unary_or_primary_expression

import inno.jago.ast.model.expression.unary_expression.IndexExpressionNode
import inno.jago.cppgen.expression.translateToCode

fun IndexExpressionNode.translateToCode(): String =
    primaryExpression.translateToCode() + "[" + expression.translateToCode() + "]"
