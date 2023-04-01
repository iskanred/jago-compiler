package inno.jago

import org.antlr.v4.runtime.CharStream
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.Lexer
import org.antlr.v4.runtime.Token

abstract class GoLexerBase(input: CharStream) :
    Lexer(CharStreams.fromString(input.toString() + System.lineSeparator())) {
    /**
     * Type of last token inside visible (not HIDDEN) channel
     */
    var lastTokenType: Int

    init {
        /*
            Since Antlr does not recognize rules with EOF token inside,
             we have to add line separator to the end of input CharStream
             in the constructor of the lexer
         */
        lastTokenType = -4
    }

    override fun emit(token: Token) {
        super.emit(token)

        // return type of last token inside visible (not HIDDEN) channel
        if (_channel != HIDDEN) lastTokenType = token.type
    }
}