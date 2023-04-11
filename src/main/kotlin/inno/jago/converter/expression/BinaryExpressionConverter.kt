package inno.jago.converter.expression

import inno.jago.UnreachableCodeException
import inno.jago.ast.expression.binary_expression.AddOperator
import inno.jago.ast.expression.binary_expression.AddOperators
import inno.jago.ast.expression.binary_expression.BinaryOperator
import inno.jago.ast.expression.binary_expression.LogicOperator
import inno.jago.ast.expression.binary_expression.LogicOperators
import inno.jago.ast.expression.binary_expression.MulOperator
import inno.jago.ast.expression.binary_expression.MulOperators
import inno.jago.ast.expression.binary_expression.RelationOperator
import inno.jago.ast.expression.binary_expression.RelationOperators
import inno.jago.converter.toPos

fun GoParser.Binary_opContext.toBinaryOperator(): BinaryOperator {
    LOGICAL_OR()?.let {
        return LogicOperator(
            pos = toPos(),
            operator = LogicOperators.LOGIC_OR
        )
    }

    LOGICAL_AND()?.let {
        return LogicOperator(
            pos = toPos(),
            operator = LogicOperators.LOGIC_AND
        )
    }

    rel_op()?.let {
        return RelationOperator(
            pos = toPos(),
            operator = it.toRelationOperators()
        )
    }

    add_op()?.let {
        return AddOperator(
            pos = toPos(),
            operator = it.toAddOperators()
        )
    }

    mul_op()?.let {
        return MulOperator(
            pos = toPos(),
            operator = it.toMulOperators()
        )
    }

    throw UnreachableCodeException()
}

fun GoParser.Rel_opContext.toRelationOperators(): RelationOperators {
    EQUALS()?.let {
        return RelationOperators.EQUALS
    }

    NOT_EQUALS()?.let {
        return RelationOperators.NOT_EQUALS
    }

    LESS()?.let {
        return RelationOperators.LESS
    }

    LESS_OR_EQUALS()?.let {
        return RelationOperators.LESS_OR_EQUALS
    }

    GREATER()?.let {
        return RelationOperators.GREATER
    }

    GREATER_OR_EQUALS()?.let {
        return RelationOperators.GREATER_OR_EQUALS
    }

    throw UnreachableCodeException()
}

fun GoParser.Add_opContext.toAddOperators(): AddOperators {
    PLUS()?.let {
        return AddOperators.PLUS
    }

    MINUS()?.let {
        return AddOperators.MINUS
    }

    OR()?.let {
        return AddOperators.OR
    }

    CARET()?.let {
        return AddOperators.CARET
    }

    throw UnreachableCodeException()
}

fun GoParser.Mul_opContext.toMulOperators(): MulOperators {
    ASTERISK()?.let {
        return MulOperators.ASTERISK
    }

    DIV()?.let {
        return MulOperators.DIV
    }

    MOD()?.let {
        return MulOperators.MOD
    }

    LSHIFT()?.let {
        return MulOperators.LSHIFT
    }

    RSHIFT()?.let {
        return MulOperators.RSHIFT
    }

    AMPERSAND()?.let {
        return MulOperators.AMPERSAND
    }

    BIT_CLEAR()?.let {
        return MulOperators.BIT_CLEAR
    }

    throw UnreachableCodeException()
}
