package inno.jago.converter.expression

import inno.jago.UnreachableCodeException
import inno.jago.ast.expression.binary_expression.LogicOperator
import inno.jago.ast.expression.binary_expression.LogicOperators
import inno.jago.ast.expression.unary_expression.UnaryOperatorNode
import inno.jago.ast.expression.unary_expression.UnaryOperators
import inno.jago.converter.toPos
import java.util.function.UnaryOperator
import javax.print.StreamPrintService

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


