package inno.jago.ast.converter.expression.primary_expression

import inno.jago.common.UnreachableCodeException
import inno.jago.ast.model.expression.ExpressionNode
import inno.jago.ast.model.expression.unary_expression.ApplicationExpressionNode
import inno.jago.ast.model.expression.unary_expression.IndexExpressionNode
import inno.jago.ast.model.expression.unary_expression.PrimaryExpressionNode
import inno.jago.ast.converter.common.toPos
import inno.jago.ast.converter.expression.toExpressionNode
import inno.jago.ast.converter.expression.primary_expression.operand.toOperandNode
import inno.jago.ast.converter.type.toTypeNode
import inno.jago.ast.model.expression.unary_expression.ConversionNode
import inno.jago.common.EntityNotSupportedException

@Suppress("ReturnCount", "ThrowsCount")
fun GoParser.PrimaryExprContext.toPrimaryExpressionNode(): PrimaryExpressionNode {
    operand()?.let {
        return it.toOperandNode()
    }

    conversion()?.let {
        return it.toConversionNode()
    }

    methodExpr()?.let {
        throw EntityNotSupportedException("Methods")
    }

    selector()?.let {
        throw EntityNotSupportedException("Methods and Imports")
    }

    index()?.let {
        return IndexExpressionNode(
            pos = toPos(),
            primaryExpression = primaryExpr().toPrimaryExpressionNode(),
            expression = it.expression().toExpressionNode()
        )
    }

    arguments()?.let {
        val primaryExpr = primaryExpr()
        val leftExprEntity = primaryExpr.toPrimaryExpressionNode()
        val rightExpressions = it.toExpressionNodes()

        primaryExpr.text.toTypeNode(primaryExpr.toPos()).also { typeNode ->
            if (rightExpressions.size == 1 && typeNode != null) {
                return ConversionNode(
                    pos = toPos(),
                    type = typeNode,
                    expression = rightExpressions.first()
                )
            }
        }

        return ApplicationExpressionNode(
            pos = toPos(),
            leftExpression = leftExprEntity,
            expressions = rightExpressions
        )
    }

    slice()?.let {
        throw EntityNotSupportedException("Slice")
    }

    typeAssertion()?.let {
        throw EntityNotSupportedException("TypeAssertion")
    }

    throw UnreachableCodeException()
}

private fun GoParser.ArgumentsContext.toExpressionNodes(): List<ExpressionNode> {
    type()?.let {
        throw EntityNotSupportedException("Types in function arguments")
    }
    return expressionList()?.expression()?.map { it.toExpressionNode() } ?: emptyList()
}
