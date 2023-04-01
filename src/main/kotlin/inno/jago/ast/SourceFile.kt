package inno.jago.ast

import inno.jago.ast.toplevel.TopLevelDecl
import inno.jago.lexer.Span

class SourceFile(
    span: Span,
    val packageName: Package,
    val imports: List<Import>,
    val topLevelDecls: List<TopLevelDecl>
) : Entity(span)
