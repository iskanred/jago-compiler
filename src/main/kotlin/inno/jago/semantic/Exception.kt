package inno.jago.semantic

import inno.jago.ast.model.expression.ExpressionNode
import inno.jago.common.JaGoException
import inno.jago.lexer.Pos
import inno.jago.semantic.model.Type

open class SemanticException(msg: String) : JaGoException(msg)

class WrongTypeException(vararg expectedType: Type, actualType: Type, pos: Pos)
    : SemanticException("$pos> Expected [${expectedType.joinToString()}] type but got [$actualType]")

class NamedEntityAlreadyExistsException(identifier: String, pos: Pos)
    : SemanticException("$pos> Entity '$identifier' already exists")

class FuncEntityAlreadyExistsException(identifier: String, type: Type.FuncType, pos: Pos)
    : SemanticException("$pos> Function $identifier(${type.paramTypes.joinToString()})")

class StructEntityAlreadyExistsException(identifier: String, type: Type.StructType, pos: Pos)
    : SemanticException("Struct $identifier(${type.fields}) at $pos")
class VarDeclMustPresentTypeOrExpressionException(varIdentifier: String, pos: Pos) : SemanticException(
    "$pos> The variable declaration '$varIdentifier' must specify the type or the assigned expression"
)

class ReturnInWrongScopeException(pos : Pos)
    : SemanticException("$pos> Return statement at must be inside function scope")

class NoSuchEntityInCurrentScopeException(identifier: String, pos : Pos)
    : SemanticException("$pos> No such entity '$identifier'")

class NoSuchFunctionException(argTypes: List<Type>, pos : Pos)
    : SemanticException("$pos> No such function with argument types (${argTypes.joinToString()})")

class NonCastableTypeException(from: Type, to: Type, pos: Pos)
    : SemanticException("$pos> Cannot cast from $from to $to")

class BreakIsNotInLoopException(pos: Pos)
    : SemanticException("$pos> 'break' is not in a loop")

class ContinueIsNotInLoopException(pos: Pos)
    : SemanticException("$pos> continue is not in a loop")

class IsNotAssignableExpression(expression: ExpressionNode)
    : SemanticException("${expression.pos}> ${expression.javaClass.simpleName} is not assignable")
