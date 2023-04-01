package inno.jago.ast

import inno.jago.lexer.Span
import inno.jago.lexer.Token

class Entity(val span: Span) {
    constructor(token: Token) : this(token.span)
}