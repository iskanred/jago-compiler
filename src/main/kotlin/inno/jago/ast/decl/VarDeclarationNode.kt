package inno.jago.ast.decl

class VarDeclarationNode(
    pos: Pos,
    identifier: String,
    type: Type,
    expression: Expression
) : DeclarationNode(pos, identifier, type, expression)
