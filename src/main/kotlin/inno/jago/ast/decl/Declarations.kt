package inno.jago.ast.decl

import inno.jago.ast.statement.BlockStatementNode
import inno.jago.ast.expression.ExpressionNode
import inno.jago.ast.type.TypeNode
import inno.jago.entity.Entity
import inno.jago.lexer.Pos

sealed class TopLevelDeclNode(pos: Pos)
    : Entity(pos = pos)

class FunctionDeclarationNode (
    pos: Pos,
    val functionName : String,
    val signature : SignatureNode,
    val functionBody : BlockStatementNode
) : TopLevelDeclNode(pos = pos)

sealed class DeclarationNode(
    pos: Pos,
    val identifier: String,
    val type: TypeNode,
    val expression: ExpressionNode
) : TopLevelDeclNode(pos = pos)


class ConstDeclarationNode(
    pos: Pos,
    identifier: String,
    type: TypeNode,
    expression: ExpressionNode
) : DeclarationNode(
    pos = pos,
    identifier = identifier,
    type = type,
    expression = expression
)

class TypeDeclarationNode(
    pos: Pos,
    identifier: String,
    type: TypeNode,
    expression: ExpressionNode
) : DeclarationNode(
    pos = pos,
    identifier = identifier,
    type = type,
    expression = expression
)

class VarDeclarationNode(
    pos: Pos,
    identifier: String,
    type: TypeNode,
    expression: ExpressionNode
) : DeclarationNode(
    pos = pos,
    identifier = identifier,
    type = type,
    expression = expression
)
