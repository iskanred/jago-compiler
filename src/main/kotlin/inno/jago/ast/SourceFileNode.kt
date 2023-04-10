package inno.jago.ast

import inno.jago.ast.decl.TopLevelDeclNode
import inno.jago.entity.Entity
import inno.jago.lexer.Pos

class SourceFileNode(
    pos: Pos,
    val packageName: PackageNode,
    val importNodes: List<ImportNode>,
    val topLevelDecls: List<TopLevelDeclNode>
) : Entity(pos = pos)
