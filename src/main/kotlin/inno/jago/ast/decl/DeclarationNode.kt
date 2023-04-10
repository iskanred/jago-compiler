package inno.jago.ast.decl

import inno.jago.lexer.Pos
import inno.jago.ast.type.TypeNode
import inno.jago.ast.expression.ExpressionNode

open class DeclarationNode(
    pos: Pos,
    val identifier: String,
    val type: TypeNode,
    val expression: ExpressionNode
) : TopLevelDeclNode(pos = pos)
