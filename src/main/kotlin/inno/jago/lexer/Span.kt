package inno.jago.lexer

class Span(
    var begin: Pos,
    var end: Pos
) {

    constructor(span: Span) : this(span.begin, span.end)

    constructor(token: Token) : this(token.span.begin, token.span.end)

    constructor(b: Token, e: Token) : this(b.span.begin, e.span.end)

    fun report() {
        print("(")
        begin.report()
        print(",")
        end.report()
        print(")")
    }

    override fun toString(): String {
        return "Line " + begin.line + ", Char " + begin.line
    }
}