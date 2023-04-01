package ast

import lexer.Span
import lexer.Token

class Entity(val span: Span) {
    constructor(token: Token) : this(token.span)
}