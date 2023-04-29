package inno.jago.ast

import inno.jago.exception.JaGoException
import inno.jago.lexer.Pos

sealed class ASTBuildException(msg: String) : JaGoException(msg)

class WrongNumberOfExpressionsException(expected: Int, actual: Int)
    : ASTBuildException("Expected $expected or func expressions, got $actual expression")

class WrongBinaryExpressionNumberException : ASTBuildException("Expected two expressions around binary operator")

class EntityNotSupportedException(entityName: String) : ASTBuildException("$entityName are not supported")

class FunctionIdentifiersException
    : ASTBuildException("Function can only have parameters with identifiers or not at all")

class UnknownTypeException(
    pos: Pos,
    entityName: String,
) : ASTBuildException("Unknown type '$entityName' at $pos")
