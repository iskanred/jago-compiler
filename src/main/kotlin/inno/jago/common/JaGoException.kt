package inno.jago.common

import inno.jago.lexer.Pos
import javax.naming.OperationNotSupportedException

open class JaGoException(msg: String) : Exception(msg)

class UnreachableCodeException : OperationNotSupportedException("Unreachable code")


class WrongNumberOfExpressionsException(expected: Int, actual: Int, pos: Pos)
    : JaGoException("Expected $expected or func expressions, got $actual expression at $pos")
