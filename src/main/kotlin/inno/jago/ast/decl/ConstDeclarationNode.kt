package inno.jago.ast.decl

import inno.jago.lexer.Pos

class ConstDeclarationNode(
    pos: Pos,
    identifier: String,
    type: Type,
    expression: Expression
) : DeclarationNode(pos, identifier, type, expression)
