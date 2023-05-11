package inno.jago.ast

import inno.jago.ast.model.expression.ExpressionNode
import inno.jago.common.JaGoException
import inno.jago.lexer.Pos

open class ASTBuildException(msg: String) : JaGoException(msg)

class WrongBinaryExpressionNumberException(pos: Pos) :
    ASTBuildException("$pos> Expected two expressions around binary operator")


class FunctionIdentifiersException(pos: Pos)
    : ASTBuildException("$pos> Function can only have parameters with identifiers or do not have at all")

class UnknownTypeException(
    pos: Pos,
    entityName: String,
) : ASTBuildException("$pos> Unknown type '$entityName'")

class IncorrectNumberLiteralException(literal: String, pos: Pos)
    : ASTBuildException("$pos> Incorrect number literal '$literal'")

class ArrayLengthNotIntegerLiteralException(expressionNode: ExpressionNode) : ASTBuildException(
    "${expressionNode.pos}> Length of array must be integer literal constant, but got something else"
)
