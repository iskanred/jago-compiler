package inno.jago.ast.model.global

import inno.jago.ast.model.ASTNode
import inno.jago.ast.model.decl.TopLevelDeclNode
import inno.jago.lexer.Pos

class SourceFileNode(
    pos: Pos,
    val packageName: PackageNode,
    val importNodes: List<ImportNode>,
    val topLevelDecls: List<TopLevelDeclNode>
) : ASTNode(pos = pos)
