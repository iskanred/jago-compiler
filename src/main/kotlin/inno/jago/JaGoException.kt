package inno.jago

import javax.naming.OperationNotSupportedException

sealed class JaGoException(msg: String) : Exception(msg)

class WrongNumberOfExpressions(expected: Int, actual: Int) : JaGoException(
    "Expected $expected or func expressions, got $actual expression"
)

class WrongBinaryExpressionNumber : JaGoException("Expected two expressions around binary operator")

class EntityNotSupported(entityName: String) : JaGoException("$entityName are not supported")

class FunctionIdentifiersException : JaGoException("Function can only have parameters with identifiers or not at all")

class UnreachableCodeException : OperationNotSupportedException("Unreachable code")
