package inno.jago.parser

import GoLexer
import GoParser
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.*

class ParserTest {
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
        val cs = CharStreams.fromFileName(inputFilePath)
        val lexer = GoLexer(cs)
        val tokenStream = CommonTokenStream(lexer)
        val parser = GoParser(tokenStream)
        parser.sourceFile()
        if (parser.numberOfSyntaxErrors != 0) {
            throw InputMismatchException()
        }
    }
}
