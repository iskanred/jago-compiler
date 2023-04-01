package inno.jago.ast

import inno.jago.lexer.Span

class Import(
    span: Span,
    val path: String,
    val alias: String,
    val importAll: Boolean
) : Entity(span)