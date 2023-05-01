package inno.jago.ast.converter.expression

import inno.jago.common.UnreachableCodeException
import inno.jago.ast.model.expression.unary_expression.UnaryOperatorNode
import inno.jago.ast.model.expression.unary_expression.UnaryOperators
import inno.jago.ast.converter.common.toPos

fun GoParser.Unary_opContext.toUnaryOperator(): UnaryOperatorNode {
    PLUS()?.let {
        return UnaryOperatorNode(
            pos = toPos(),
            operator = UnaryOperators.PLUS
        )
    }

    MINUS()?.let {
        return UnaryOperatorNode(
            pos = toPos(),
            operator = UnaryOperators.MINUS
        )
    }

    EXCLAMATION()?.let {
        return UnaryOperatorNode(
            pos = toPos(),
            operator = UnaryOperators.EXCLAMATION
        )
    }

    CARET()?.let {
        return UnaryOperatorNode(
            pos = toPos(),
            operator = UnaryOperators.CARET
        )
    }

    ASTERISK()?.let {
        return UnaryOperatorNode(
            pos = toPos(),
            operator = UnaryOperators.ASTERISK
        )
    }

    AMPERSAND()?.let {
        return UnaryOperatorNode(
            pos = toPos(),
            operator = UnaryOperators.AMPERSAND
        )
    }

    RECEIVE()?.let {
        return UnaryOperatorNode(
            pos = toPos(),
            operator = UnaryOperators.RECEIVE
        )
    }

    throw UnreachableCodeException()
}


