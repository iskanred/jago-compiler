package inno.jago.exception

import javax.naming.OperationNotSupportedException

open class JaGoException(msg: String) : Exception(msg)

class UnreachableCodeException : OperationNotSupportedException("Unreachable code")
