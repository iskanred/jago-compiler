package inno.jago.exception

import inno.jago.lexer.Pos
import inno.jago.semantic.SemanticEntity
import inno.jago.semantic.Type
import javax.naming.OperationNotSupportedException

class UnreachableCodeException : OperationNotSupportedException("Unreachable code")

sealed class JaGoException(msg: String) : Exception(msg)

sealed class ASTBuildException(msg: String) : JaGoException(msg)

class WrongNumberOfExpressions(expected: Int, actual: Int)
    : ASTBuildException("Expected $expected or func expressions, got $actual expression")

class WrongBinaryExpressionNumber : ASTBuildException("Expected two expressions around binary operator")

class EntityNotSupported(entityName: String) : ASTBuildException("$entityName are not supported")

class FunctionIdentifiersException
    : ASTBuildException("Function can only have parameters with identifiers or not at all")

class UnknownTypeException(
    pos: Pos,
    entityName: String,
) : ASTBuildException("Unknown type '$entityName' at $pos")


sealed class SemanticException(msg: String) : JaGoException(msg)

class WrongTypeException(expectedType: Type, actual: SemanticEntity)
    : SemanticException("Expected [$expectedType] type but got [${actual.type}] at ${actual.pos}")

class WrongNumberOfArguments(
    expectedNum: Int,
    actualNum: Int,
    pos: Pos
) : SemanticException("Wrong number of arguments at $pos: expected $expectedNum, but got $actualNum")

class EntityAlreadyExistsException(entity: SemanticEntity)
    : SemanticException("${entity.entityType} with name '${entity.text}' already exists at ${entity.pos}")
