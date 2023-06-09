package inno.jago.lexer

import GoLexer
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.Token
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class LexerTest {

    @Test
    fun testLexer1() {
        val tokens = getTokensFromFile("go_src_1.go")
        Assertions.assertArrayEquals(
            tokens.toTypedArray(), arrayOf(
                "LINE_COMMENT",
                "LINE_COMMENT",
                "PACKAGE", "IDENTIFIER", "END",
                "IMPORT", "STRING_LIT", "END",
                "FUNC", "IDENTIFIER", "L_PAREN", "R_PAREN", "L_CURLY",
                "IDENTIFIER", "DOT", "IDENTIFIER", "L_PAREN", "RUNE_LIT", "R_PAREN", "END",
                "IDENTIFIER", "DOT", "IDENTIFIER", "L_PAREN", "STRING_LIT", "R_PAREN", "END",
                "R_CURLY", "END"
            )
        )
    }

    @Test
    fun testLexer2() {
        val tokens = getTokensFromFile("go_src_2.go")
        Assertions.assertArrayEquals(
            tokens.toTypedArray(), arrayOf(
                "LINE_COMMENT",
                "PACKAGE", "IDENTIFIER", "END",
                "IMPORT", "L_PAREN", "STRING_LIT", "END",
                "STRING_LIT", "END",
                "R_PAREN", "END",
                "TYPE", "IDENTIFIER", "STRUCT", "L_CURLY", "LINE_COMMENT",
                "IDENTIFIER", "IDENTIFIER", "DOT", "IDENTIFIER", "STRING_LIT", "END",
                "IDENTIFIER", "IDENTIFIER", "STRING_LIT", "END",
                "IDENTIFIER", "IDENTIFIER", "STRING_LIT", "END",
                "IDENTIFIER", "L_BRACKET", "R_BRACKET", "IDENTIFIER", "STRING_LIT", "END",
                "R_CURLY", "END",
                "GENERAL_COMMENT",
                "FUNC", "L_PAREN", "IDENTIFIER", "IDENTIFIER", "R_PAREN", "IDENTIFIER", "L_PAREN",
                "R_PAREN", "IDENTIFIER", "L_CURLY", "RETURN", "IDENTIFIER", "DOT", "IDENTIFIER",
                "L_PAREN", "STRING_LIT", "COMMA", "IDENTIFIER", "DOT", "IDENTIFIER", "COMMA",
                "IDENTIFIER", "DOT", "IDENTIFIER", "COMMA", "IDENTIFIER", "DOT", "IDENTIFIER",
                "R_PAREN", "END",
                "R_CURLY", "END",
                "FUNC", "IDENTIFIER", "L_PAREN", "R_PAREN", "L_CURLY", "IDENTIFIER", "DECLARE_ASSIGN",
                "AMPERSAND", "IDENTIFIER", "L_CURLY", "IDENTIFIER", "COLON", "INT_LIT", "COMMA",
                "IDENTIFIER", "COLON", "STRING_LIT", "R_CURLY", "END", "IDENTIFIER", "DOT",
                "IDENTIFIER", "ASSIGN", "L_BRACKET", "R_BRACKET", "IDENTIFIER", "L_CURLY",
                "STRING_LIT", "COMMA", "STRING_LIT", "R_CURLY", "END",
                "IDENTIFIER", "COMMA", "IDENTIFIER", "DECLARE_ASSIGN", "IDENTIFIER", "DOT",
                "IDENTIFIER", "L_PAREN", "IDENTIFIER", "COMMA", "STRING_LIT", "COMMA", "STRING_LIT",
                "R_PAREN", "END",
                "IDENTIFIER", "DOT", "IDENTIFIER", "L_PAREN", "IDENTIFIER", "L_PAREN", "IDENTIFIER",
                "R_PAREN", "R_PAREN", "END",
                "IDENTIFIER", "DOT", "IDENTIFIER", "L_PAREN", "IDENTIFIER", "DOT", "IDENTIFIER",
                "PLUS", "IDENTIFIER", "L_PAREN", "IDENTIFIER", "R_PAREN", "R_PAREN", "END",
                "VAR", "IDENTIFIER", "IDENTIFIER", "END",
                "IF", "IDENTIFIER", "DECLARE_ASSIGN", "IDENTIFIER", "DOT", "IDENTIFIER", "L_PAREN",
                "IDENTIFIER", "COMMA", "AMPERSAND", "IDENTIFIER", "R_PAREN", "END",
                "IDENTIFIER", "NOT_EQUALS", "IDENTIFIER", "L_CURLY", "IDENTIFIER", "L_PAREN",
                "IDENTIFIER", "R_PAREN", "END",
                "R_CURLY", "END",
                "IDENTIFIER", "DOT", "IDENTIFIER", "L_PAREN", "IDENTIFIER", "R_PAREN", "END",
                "IDENTIFIER", "DECLARE_ASSIGN", "AMPERSAND", "IDENTIFIER", "L_CURLY", "IDENTIFIER",
                "COLON", "INT_LIT", "COMMA", "IDENTIFIER", "COLON", "STRING_LIT", "R_CURLY", "END",
                "IDENTIFIER", "DOT", "IDENTIFIER", "ASSIGN", "L_BRACKET", "R_BRACKET", "IDENTIFIER",
                "L_CURLY", "STRING_LIT", "COMMA", "STRING_LIT", "R_CURLY", "END",
                "TYPE", "IDENTIFIER", "STRUCT", "L_CURLY", "IDENTIFIER", "IDENTIFIER", "DOT",
                "IDENTIFIER", "STRING_LIT", "END",
                "IDENTIFIER", "L_BRACKET", "R_BRACKET", "ASTERISK", "IDENTIFIER", "STRING_LIT", "END",
                "R_CURLY", "END",
                "IDENTIFIER", "DECLARE_ASSIGN", "AMPERSAND", "IDENTIFIER", "L_CURLY", "R_CURLY", "END",
                "IDENTIFIER", "DOT", "IDENTIFIER", "ASSIGN", "L_BRACKET", "R_BRACKET", "ASTERISK",
                "IDENTIFIER", "L_CURLY", "IDENTIFIER", "COMMA", "IDENTIFIER", "R_CURLY", "END",
                "IDENTIFIER", "COMMA", "IDENTIFIER", "ASSIGN", "IDENTIFIER", "DOT", "IDENTIFIER",
                "L_PAREN", "IDENTIFIER", "COMMA", "STRING_LIT", "COMMA", "STRING_LIT", "R_PAREN", "END",
                "IDENTIFIER", "DOT", "IDENTIFIER", "L_PAREN", "IDENTIFIER", "L_PAREN", "IDENTIFIER", "R_PAREN",
                "R_PAREN", "END",
                "R_CURLY", "END"
            )
        )
    }

    @Test
    fun testLexer3() {
        val tokens = getTokensFromFile("go_src_3.go")
        Assertions.assertArrayEquals(
            tokens.toTypedArray(), arrayOf(
                "PACKAGE", "IDENTIFIER", "END",
                "GENERAL_COMMENT", "FUNC", "IDENTIFIER", "L_PAREN", "R_PAREN", "L_CURLY",
                "R_CURLY", "LINE_COMMENT", "END"
            )
        )
    }

    private fun getTokensFromFile(inputFileName: String): List<String> {
        val cs = CharStreams.fromFileName("src/test/resources/tests/lexer/$inputFileName")
        val lexer = GoLexer(cs)
        val tokenList: MutableList<out Token> = lexer.allTokens
        val tokens = ArrayList<String>(tokenList.size)

        tokenList.forEach { e ->
            tokens.add(lexer.vocabulary.getSymbolicName(e.type))
        }
        tokens.removeIf { it == "WHITESPACE" } // remove whitespace-tokens from token
        return tokens
    }
}
