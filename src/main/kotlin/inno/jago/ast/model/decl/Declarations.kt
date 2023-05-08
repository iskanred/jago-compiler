package inno.jago.ast.model.decl

import inno.jago.ast.model.statement.BlockStatementNode
import inno.jago.ast.model.expression.ExpressionNode
import inno.jago.ast.model.signature.SignatureNode
import inno.jago.ast.model.type.TypeNode
import inno.jago.ast.model.ASTNode
import inno.jago.lexer.Pos

sealed class TopLevelDeclNode(pos: Pos)
    : ASTNode(pos = pos)

class FunctionDeclarationNode (
    pos: Pos,
    val functionName: String,
    val signature: SignatureNode,
    val functionBody: BlockStatementNode
) : TopLevelDeclNode(pos = pos)

sealed class DeclarationNode(
    pos: Pos,
    val identifier: String,
    val type: TypeNode?,
    val expression: ExpressionNode?
) : TopLevelDeclNode(pos = pos)

class ConstDeclarationNode(
    pos: Pos,
    identifier: String,
    type: TypeNode?,
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
    type: TypeNode?,
    expression: ExpressionNode?,
    // Позиция переменной в строке для определения ее типа на этапе проверки типов
    val positionInRow: Int,
    val numberOfDeclarationsInRow: Int
) : DeclarationNode(
    pos = pos,
    identifier = identifier,
    type = type,
    expression = expression
)
