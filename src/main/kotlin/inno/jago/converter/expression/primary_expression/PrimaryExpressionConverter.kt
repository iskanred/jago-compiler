package inno.jago.converter.expression.primary_expression

import inno.jago.exception.EntityNotSupported
import inno.jago.exception.UnreachableCodeException
import inno.jago.ast.expression.ExpressionNode
import inno.jago.ast.expression.unary_expression.ApplicationExpressionNode
import inno.jago.ast.expression.unary_expression.IndexExpressionNode
import inno.jago.ast.expression.unary_expression.PrimaryExpressionNode
import inno.jago.ast.expression.unary_expression.SelectorExpressionNode
import inno.jago.converter.common.toPos
import inno.jago.converter.expression.toExpressionNode
import inno.jago.converter.expression.primary_expression.operand.toOperandNode

fun GoParser.PrimaryExprContext.toPrimaryExpressionNode(): PrimaryExpressionNode {
    operand()?.let {
        return it.toOperandNode()
    }

    conversion()?.let {
        return it.toConversionNode()
    }

    methodExpr()?.let {
        throw EntityNotSupported("MethodExpression")
    }

    selector()?.let {
        return SelectorExpressionNode(
            pos = toPos(),
            primaryExpression = primaryExpr().toPrimaryExpressionNode(),
            selector = it.IDENTIFIER().text
        )
    }

    index()?.let {
        return IndexExpressionNode(
            pos = toPos(),
            primaryExpression = primaryExpr().toPrimaryExpressionNode(),
            expression = it.expression().toExpressionNode()
        )
    }

    arguments()?.let {
        return ApplicationExpressionNode(
            pos = toPos(),
            leftExpression = primaryExpr().toPrimaryExpressionNode(),
            expressions = it.toExpressionNodes()
        )
    }

    slice()?.let {
        throw EntityNotSupported("Slice")
    }

    typeAssertion()?.let {
        throw EntityNotSupported("TypeAssertion")
    }

    throw UnreachableCodeException()
}

private fun GoParser.ArgumentsContext.toExpressionNodes(): List<ExpressionNode> {
    type()?.let {
        throw EntityNotSupported("Types in function arguments")
    }
    return expressionList()?.expression()?.map { it.toExpressionNode() } ?: emptyList()
}
