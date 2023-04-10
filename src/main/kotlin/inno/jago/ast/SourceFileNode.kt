package inno.jago.ast

import inno.jago.ast.decl.TopLevelDecl
import inno.jago.entity.Entity
import inno.jago.lexer.Pos

class SourceFileNode(
    pos: Pos,
    val packageName: PackageNode,
    val importNodes: List<ImportNode>,
    val topLevelDecls: List<TopLevelDecl>
) : Entity(pos = pos)
