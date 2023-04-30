package inno.jago.semantic

import inno.jago.exception.JaGoException
import inno.jago.lexer.Pos
import inno.jago.semantic.model.SemanticEntity
import inno.jago.semantic.model.Type

sealed class SemanticException(msg: String) : JaGoException(msg)

class WrongTypeException(vararg expectedType: Type, actual: SemanticEntity)
    : SemanticException("Expected [${expectedType.joinToString()}] type but got [${actual.type}] at ${actual.pos}")

class WrongNumberOfArguments(
    expectedNum: Int,
    actualNum: Int,
    pos: Pos
) : SemanticException("Wrong number of arguments at $pos: expected $expectedNum, but got $actualNum")

class EntityAlreadyExistsException(entity: SemanticEntity)
    : SemanticException("${entity.entityType} with name '${entity.identifier}' already exists at ${entity.pos}")

class VarDeclMustPresentTypeOrExpressionException(varIdentifier: String, pos: Pos) : SemanticException(
    "The variable declaration '$varIdentifier' must specify the type or the assigned expression at $pos"
)

class ReturnInGlobalScopeException(pos : Pos)
    : SemanticException("Return statement at $pos cannot exist in global scope")

class NoSuchEntityInCurrentScopeException(identifier: String, pos : Pos)
    : SemanticException("No such variable $identifier at $pos")

class NonCastableTypeException(from: Type, to: Type, pos: Pos)
    : SemanticException("Cannot cast from $from to $to at $pos")

class BreakIsNotInLoopException(pos: Pos)
    : SemanticException("break is not in a loop at $pos")

class ContinueIsNotInLoopException(pos: Pos)
    : SemanticException("continue is not in a loop at $pos")
