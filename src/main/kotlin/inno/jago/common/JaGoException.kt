package inno.jago.common

import inno.jago.lexer.Pos
import javax.naming.OperationNotSupportedException

open class JaGoException(msg: String) : Exception(msg)

class UnreachableCodeException : OperationNotSupportedException("Unreachable code")


class WrongNumberOfExpressionsException(expected: Int, actual: Int, pos: Pos)
    : JaGoException("$pos> Expected $expected or func (with tuple return type) expressions, got $actual expression")

class EntityNotSupportedException(entityName: String) : JaGoException("$entityName are not supported")

class InputArgumentsException(msg: String) : JaGoException(msg)
