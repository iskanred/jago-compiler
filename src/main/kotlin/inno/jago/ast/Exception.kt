package inno.jago.ast

import inno.jago.ast.model.expression.ExpressionNode
import inno.jago.common.JaGoException
import inno.jago.lexer.Pos

open class ASTBuildException(msg: String) : JaGoException(msg)

class WrongBinaryExpressionNumberException : ASTBuildException("Expected two expressions around binary operator")


class FunctionIdentifiersException
    : ASTBuildException("Function can only have parameters with identifiers or do not have at all")

class UnknownTypeException(
    pos: Pos,
    entityName: String,
) : ASTBuildException("Unknown type '$entityName' at $pos")

class IncorrectNumberLiteral(literal: String, pos: Pos)
    : ASTBuildException("Incorrect number literal '$literal' at $pos")

class ArrayLengthNotIntegerLiteralException(expressionNode: ExpressionNode) : ASTBuildException(
    "Length of array must be integer literal constant, but got something else at ${expressionNode.pos}"
)
