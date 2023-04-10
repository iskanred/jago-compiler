package inno.jago.ast.decl

import inno.jago.ast.type.TypeNode

class TypeDeclarationNode(
    pos: Pos,
    identifier: String,
    type: TypeNode,
    expression: Expression
) : DeclarationNode(pos, identifier, type, expression)
