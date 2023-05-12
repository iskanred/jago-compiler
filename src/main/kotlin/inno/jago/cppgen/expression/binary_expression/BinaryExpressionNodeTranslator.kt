package inno.jago.cppgen.expression.binary_expression

import inno.jago.ast.model.expression.binary_expression.AddOperator
import inno.jago.ast.model.expression.binary_expression.AddOperators
import inno.jago.ast.model.expression.binary_expression.BinaryExpression
import inno.jago.ast.model.expression.binary_expression.BinaryOperator
import inno.jago.ast.model.expression.binary_expression.LogicOperator
import inno.jago.ast.model.expression.binary_expression.LogicOperators
import inno.jago.ast.model.expression.binary_expression.MulOperator
import inno.jago.ast.model.expression.binary_expression.MulOperators
import inno.jago.ast.model.expression.binary_expression.RelationOperator
import inno.jago.ast.model.expression.binary_expression.RelationOperators
import inno.jago.cppgen.expression.translateToCode

fun BinaryExpression.translateToCode(): String =
    "(${leftExpression.translateToCode()}) ${binaryOperator.translateToCode()} (${rightExpression.translateToCode()})"

fun BinaryOperator.translateToCode(): String = when (this){
    is RelationOperator -> operator.translateToCode()
    is AddOperator -> operator.translateToCode()
    is MulOperator -> operator.translateToCode()
    is LogicOperator -> operator.translateToCode()
}

fun LogicOperators.translateToCode(): String = when (this) {
    LogicOperators.LOGIC_OR -> "||"
    LogicOperators.LOGIC_AND -> "&&"
}

fun MulOperators.translateToCode(): String = when (this) {
    MulOperators.ASTERISK -> "*"
    MulOperators.DIV -> "/"
    MulOperators.MOD -> "%"
    MulOperators.LSHIFT -> "<<"
    MulOperators.RSHIFT -> ">>"
    MulOperators.AMPERSAND -> "&"
    MulOperators.BIT_CLEAR -> "&~"
}

fun AddOperators.translateToCode(): String = when (this) {
    AddOperators.PLUS -> "+"
    AddOperators.MINUS -> "-"
    AddOperators.OR -> "|"
    AddOperators.CARET -> "^"
}

fun RelationOperators.translateToCode(): String = when (this) {
    RelationOperators.EQUALS -> "=="
    RelationOperators.NOT_EQUALS -> "!="
    RelationOperators.LESS -> "<"
    RelationOperators.LESS_OR_EQUALS -> "<="
    RelationOperators.GREATER -> ">"
    RelationOperators.GREATER_OR_EQUALS -> ">="
}
