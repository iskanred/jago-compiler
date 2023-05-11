package inno.jago.parser

import GoLexer
import GoParser
import inno.jago.getFilesSequence
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.*
import java.util.stream.Stream
import kotlin.streams.asStream

class ParserTest {
    @ParameterizedTest
    @MethodSource("getGoodFilePaths")
    fun testParserGood(filename: String) {
        runParser(filename)
    }

    @ParameterizedTest
    @MethodSource("getBadFilePaths")
    fun testParserBad(filename: String) {
        assertThrows<InputMismatchException> {
            runParser(filename)
        }
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

    companion object {
        private const val GOOD_DIR_PATH = "src/test/resources/tests/parser/good"
        private const val BAD_DIR_PATH = "src/test/resources/tests/parser/bad"

        @JvmStatic
        fun getGoodFilePaths(): Stream<Arguments> = getFilePaths(GOOD_DIR_PATH)

        @JvmStatic
        fun getBadFilePaths(): Stream<Arguments> = getFilePaths(BAD_DIR_PATH)

        private fun getFilePaths(filePath: String): Stream<Arguments> =
            getFilesSequence(filePath)
                .map { path ->
                    Arguments.of(path.toString())
                }
                .asStream()
    }
}
