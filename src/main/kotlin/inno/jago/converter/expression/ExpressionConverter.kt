package inno.jago.converter.expression

import inno.jago.EntityNotSupported
import inno.jago.UnreachableCodeException
import inno.jago.ast.expression.ExpressionNode
import inno.jago.ast.expression.binary_expression.BinaryOperator
import inno.jago.converter.toDeclarationNodes
import inno.jago.converter.toFunctionDeclarationNode

fun GoParser.ExpressionContext.toExpressionNode(): ExpressionNode {
    unaryExpr()?.let {
        TODO()
    }

    binary_op()?.let {
        var op = it.toBinaryOperator()
    }

    throw UnreachableCodeException()
}

fun GoParser.Binary_opContext.toBinaryOperator(): BinaryOperator {
    LOGICAL_OR()?.let {
        TODO()
    }

    LOGICAL_AND()?.let {

    }

    rel_op()?.let {

    }

    add_op()?.let {

    }

    mul_op()?.let {

    }

    throw UnreachableCodeException()
}
