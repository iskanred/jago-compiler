package inno.jago

import javax.naming.OperationNotSupportedException

sealed class JaGoException(msg: String) : Exception(msg)

class ConversionException(msg: String) : JaGoException(msg)

class EntityNotSupported(entityName: String) : JaGoException("$entityName are not supported")

class UnreachableCodeException : OperationNotSupportedException("Unreachable code")
