package inno.jago.lexer

class Token(
    var code: TokenCode,
    var value: Any
) {
    lateinit var span: Span

    fun report() {
        span.report()
        print(" $code ")
        print(value)
    }
}
