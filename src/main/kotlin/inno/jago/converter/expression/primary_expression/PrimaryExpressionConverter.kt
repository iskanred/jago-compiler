package inno.jago.converter.expression.primary_expression

import inno.jago.EntityNotSupported
import inno.jago.UnreachableCodeException
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

    slice()?.let {
        throw EntityNotSupported("Slice")
    }

    typeAssertion()?.let {
        throw EntityNotSupported("TypeAssertion")
    }

    arguments()?.let {
        throw EntityNotSupported("Arguments")
    }

    throw UnreachableCodeException()
}
