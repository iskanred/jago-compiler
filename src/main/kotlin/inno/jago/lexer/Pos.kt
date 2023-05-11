package inno.jago.lexer

import org.antlr.v4.runtime.Token

data class Pos (
    val line: Int,
    val startIndex: Int,
    val stopIndex: Int
) {
    constructor(token: Token) : this(token.line, token.startIndex, token.stopIndex)
    constructor(tokenStart: Token, tokenStop: Token) :
            this(tokenStart.line, tokenStart.charPositionInLine, tokenStop.charPositionInLine)

    override fun toString(): String =
        "line $line:$startIndex-$stopIndex"
}
