package inno.jago.ast.decl

import inno.jago.lexer.Pos
import inno.jago.ast.type.TypeNode
import inno.jago.ast.expression.ExpressionNode

class FunctionDeclarationNode (
    pos: Pos,
    functionName : String,
    signature : SignatureNode,
    functionBody : BlockStatementNode
) : TopLevelDeclNode(pos = pos)



