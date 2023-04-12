package inno.jago.ast.global

import inno.jago.ast.ASTNode
import inno.jago.ast.decl.TopLevelDeclNode
import inno.jago.lexer.Pos

class SourceFileNode(
    pos: Pos,
    val packageName: PackageNode,
    val importNodes: List<ImportNode>,
    val topLevelDecls: List<TopLevelDeclNode>
) : ASTNode(pos = pos)
