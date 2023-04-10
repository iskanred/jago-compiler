package inno.jago.ast.decl

import inno.jago.lexer.Pos
import inno.jago.ast.type.TypeNode
import inno.jago.ast.expression.ExpressionNode

class ConstDeclarationNode(
    pos: Pos,
    identifier: String,
    type: TypeNode,
    expression: ExpressionNode
) : DeclarationNode(
    pos = pos,
    identifier = identifier,
    type = type,
    expression = expression)
