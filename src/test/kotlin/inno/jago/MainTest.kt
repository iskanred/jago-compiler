package inno.jago

import GoLexer
import GoParser
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.Token
import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.io.IOException
import java.util.InputMismatchException

class AppTest {
    /* Testing lexer */
    @Test
    fun testLexer1() {
        val tokens = getTokensFromFile("src/test/resources/tests/go_src_1.go")
        assertArrayEquals(
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
        val tokens = getTokensFromFile("src/test/resources/tests/go_src_2.go")
        assertArrayEquals(
            tokens.toTypedArray(), arrayOf(
                "LINE_COMMENT",
                "PACKAGE",
                "IDENTIFIER",
                "END",
                "IMPORT",
                "L_PAREN",
                "STRING_LIT",
                "END",
                "STRING_LIT",
                "END",
                "R_PAREN",
                "END",
                "TYPE",
                "IDENTIFIER",
                "STRUCT",
                "L_CURLY",
                "LINE_COMMENT",
                "IDENTIFIER",
                "IDENTIFIER",
                "DOT",
                "IDENTIFIER",
                "STRING_LIT",
                "END",
                "IDENTIFIER",
                "IDENTIFIER",
                "STRING_LIT",
                "END",
                "IDENTIFIER",
                "IDENTIFIER",
                "STRING_LIT",
                "END",
                "IDENTIFIER",
                "L_BRACKET",
                "R_BRACKET",
                "IDENTIFIER",
                "STRING_LIT",
                "END",
                "R_CURLY",
                "END",
                "GENERAL_COMMENT",
                "FUNC",
                "L_PAREN",
                "IDENTIFIER",
                "IDENTIFIER",
                "R_PAREN",
                "IDENTIFIER",
                "L_PAREN",
                "R_PAREN",
                "IDENTIFIER",
                "L_CURLY",
                "RETURN",
                "IDENTIFIER",
                "DOT",
                "IDENTIFIER",
                "L_PAREN",
                "STRING_LIT",
                "COMMA",
                "IDENTIFIER",
                "DOT",
                "IDENTIFIER",
                "COMMA",
                "IDENTIFIER",
                "DOT",
                "IDENTIFIER",
                "COMMA",
                "IDENTIFIER",
                "DOT",
                "IDENTIFIER",
                "R_PAREN",
                "END",
                "R_CURLY",
                "END",
                "FUNC",
                "IDENTIFIER",
                "L_PAREN",
                "R_PAREN",
                "L_CURLY",
                "IDENTIFIER",
                "DECLARE_ASSIGN",
                "AMPERSAND",
                "IDENTIFIER",
                "L_CURLY",
                "IDENTIFIER",
                "COLON",
                "INT_LIT",
                "COMMA",
                "IDENTIFIER",
                "COLON",
                "STRING_LIT",
                "R_CURLY",
                "END",
                "IDENTIFIER",
                "DOT",
                "IDENTIFIER",
                "ASSIGN",
                "L_BRACKET",
                "R_BRACKET",
                "IDENTIFIER",
                "L_CURLY",
                "STRING_LIT",
                "COMMA",
                "STRING_LIT",
                "R_CURLY",
                "END",
                "IDENTIFIER",
                "COMMA",
                "IDENTIFIER",
                "DECLARE_ASSIGN",
                "IDENTIFIER",
                "DOT",
                "IDENTIFIER",
                "L_PAREN",
                "IDENTIFIER",
                "COMMA",
                "STRING_LIT",
                "COMMA",
                "STRING_LIT",
                "R_PAREN",
                "END",
                "IDENTIFIER",
                "DOT",
                "IDENTIFIER",
                "L_PAREN",
                "IDENTIFIER",
                "L_PAREN",
                "IDENTIFIER",
                "R_PAREN",
                "R_PAREN",
                "END",
                "IDENTIFIER",
                "DOT",
                "IDENTIFIER",
                "L_PAREN",
                "IDENTIFIER",
                "DOT",
                "IDENTIFIER",
                "PLUS",
                "IDENTIFIER",
                "L_PAREN",
                "IDENTIFIER",
                "R_PAREN",
                "R_PAREN",
                "END",
                "VAR",
                "IDENTIFIER",
                "IDENTIFIER",
                "END",
                "IF",
                "IDENTIFIER",
                "DECLARE_ASSIGN",
                "IDENTIFIER",
                "DOT",
                "IDENTIFIER",
                "L_PAREN",
                "IDENTIFIER",
                "COMMA",
                "AMPERSAND",
                "IDENTIFIER",
                "R_PAREN",
                "END",
                "IDENTIFIER",
                "NOT_EQUALS",
                "IDENTIFIER",
                "L_CURLY",
                "IDENTIFIER",
                "L_PAREN",
                "IDENTIFIER",
                "R_PAREN",
                "END",
                "R_CURLY",
                "END",
                "IDENTIFIER",
                "DOT",
                "IDENTIFIER",
                "L_PAREN",
                "IDENTIFIER",
                "R_PAREN",
                "END",
                "IDENTIFIER",
                "DECLARE_ASSIGN",
                "AMPERSAND",
                "IDENTIFIER",
                "L_CURLY",
                "IDENTIFIER",
                "COLON",
                "INT_LIT",
                "COMMA",
                "IDENTIFIER",
                "COLON",
                "STRING_LIT",
                "R_CURLY",
                "END",
                "IDENTIFIER",
                "DOT",
                "IDENTIFIER",
                "ASSIGN",
                "L_BRACKET",
                "R_BRACKET",
                "IDENTIFIER",
                "L_CURLY",
                "STRING_LIT",
                "COMMA",
                "STRING_LIT",
                "R_CURLY",
                "END",
                "TYPE",
                "IDENTIFIER",
                "STRUCT",
                "L_CURLY",
                "IDENTIFIER",
                "IDENTIFIER",
                "DOT",
                "IDENTIFIER",
                "STRING_LIT",
                "END",
                "IDENTIFIER",
                "L_BRACKET",
                "R_BRACKET",
                "ASTERISK",
                "IDENTIFIER",
                "STRING_LIT",
                "END",
                "R_CURLY",
                "END",
                "IDENTIFIER",
                "DECLARE_ASSIGN",
                "AMPERSAND",
                "IDENTIFIER",
                "L_CURLY",
                "R_CURLY",
                "END",
                "IDENTIFIER",
                "DOT",
                "IDENTIFIER",
                "ASSIGN",
                "L_BRACKET",
                "R_BRACKET",
                "ASTERISK",
                "IDENTIFIER",
                "L_CURLY",
                "IDENTIFIER",
                "COMMA",
                "IDENTIFIER",
                "R_CURLY",
                "END",
                "IDENTIFIER",
                "COMMA",
                "IDENTIFIER",
                "ASSIGN",
                "IDENTIFIER",
                "DOT",
                "IDENTIFIER",
                "L_PAREN",
                "IDENTIFIER",
                "COMMA",
                "STRING_LIT",
                "COMMA",
                "STRING_LIT",
                "R_PAREN",
                "END",
                "IDENTIFIER",
                "DOT",
                "IDENTIFIER",
                "L_PAREN",
                "IDENTIFIER",
                "L_PAREN",
                "IDENTIFIER",
                "R_PAREN",
                "R_PAREN",
                "END",
                "R_CURLY",
                "END"
            )
        )
    }

    @Test
    fun testLexer3() {
        val tokens = getTokensFromFile("src/test/resources/tests/go_src_3.go")
        assertArrayEquals(
            tokens.toTypedArray(), arrayOf(
                "PACKAGE", "IDENTIFIER", "END",
                "GENERAL_COMMENT", "FUNC", "IDENTIFIER", "L_PAREN", "R_PAREN", "L_CURLY",
                "R_CURLY", "LINE_COMMENT", "END"
            )
        )
    }

    /* Testing parser */
    @Test
    fun testParser1() {
        runParser("src/test/resources/tests/go_src_1.go")
    }

    @Test
    fun testParser2() {
        runParser("src/test/resources/tests/go_src_2.go")
    }

    @Test
    fun testParser3() {
        runParser("src/test/resources/tests/go_src_3.go")
    }

    /**
     * Error with word "Type" (incorrect: "hype")
     */
    @Test
    fun testParser4() {
        assertThrows<InputMismatchException> {
            runParser("src/test/resources/tests/go_src_4.go")
        }
    }

    @Test
    fun testParser5() {
        runParser("src/test/resources/tests/go_src_5.go")
    }

    @Test
    fun testParser6() {
        runParser("src/test/resources/tests/go_src_6.go")
    }

    /**
     * Error with initialization in import block
     */
    @Test
    fun testParser7() {
        assertThrows<InputMismatchException> {
            runParser("src/test/resources/tests/go_src_7.go")
        }
    }

    @Test
    fun testParser8() {
        runParser("src/test/resources/tests/go_src_8.go")
    }

    @Test
    fun testParser9() {
        runParser("src/test/resources/tests/go_src_9.go")
    }

    /**
     * Error with excess of right curly bracket
     */
    @Test
    fun testParser10() {
        assertThrows<InputMismatchException> {
            runParser("src/test/resources/tests/go_src_10.go")
        }
    }

    @Test
    fun testParser11() {
        runParser("src/test/resources/tests/go_src_11.go")
    }

    @Test
    fun testParser12() {
        runParser("src/test/resources/tests/go_src_12.go")
    }

    @Test
    fun testParser13() {
        runParser("src/test/resources/tests/go_src_13.go")
    }

    /**
     * Terminator after parameters in func declaration
     */
    @Test
    fun testParser14() {
        assertThrows<InputMismatchException> {
            runParser("src/test/resources/tests/go_src_14.go")
        }
    }

    @Test
    fun testParser15() {
        runParser("src/test/resources/tests/go_src_15.go")
    }

    @Test
    fun testParser16() {
        runParser("src/test/resources/tests/go_src_16.go")
    }

    @Test
    fun testParser17() {
        runParser("src/test/resources/tests/go_src_17.go")
    }

    /* Private functions */
    @Throws(InputMismatchException::class)
    private fun runParser(inputFilePath: String) {
        try {
            val cs = CharStreams.fromFileName(inputFilePath)
            val lexer = GoLexer(cs)
            val tokenStream = CommonTokenStream(lexer)
            val parser = GoParser(tokenStream)
            parser.sourceFile()
            if (parser.numberOfSyntaxErrors != 0) {
                throw InputMismatchException()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun getTokensFromFile(inputFilePath: String): List<String> {
        try {
            val cs = CharStreams.fromFileName(inputFilePath)
            val lexer = GoLexer(cs)
            val tokenList: MutableList<out Token> = lexer.allTokens
            val tokens = ArrayList<String>(tokenList.size)

            tokenList.forEach { e ->
                tokens.add(lexer.vocabulary.getSymbolicName(e.type))
            }
            tokens.removeIf { it == "WHITESPACE" } // remove whitespace-tokens from token
            return tokens
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return emptyList()
    }
}