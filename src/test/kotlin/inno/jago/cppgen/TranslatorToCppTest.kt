package inno.jago.cppgen

import GoLexer
import GoParser
import inno.jago.ast.converter.toSourceFileNode
import inno.jago.getFilesSequence
import inno.jago.semantic.TypeChecker
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.io.File
import java.util.stream.Stream
import kotlin.streams.asStream
import kotlin.test.assertEquals

class TranslatorToCppTest {

    @ParameterizedTest
    @MethodSource("getZippedInputAndOutput")
    fun test(input: String, output: String) {
        val expected = readFileDirectlyAsText(output)

        val cs = CharStreams.fromFileName(input)
        val lexer = GoLexer(cs)
        val stream = CommonTokenStream(lexer)
        val parser = GoParser(stream)

        @Suppress("UNUSED_VARIABLE") val sourceFileNode = parser.sourceFile().toSourceFileNode()
        TypeChecker(sourceFileNode = sourceFileNode).startTypeCheck()

        val actual = Translator(root = sourceFileNode).translate()
        assertEquals(expected, actual)
    }

    private fun readFileDirectlyAsText(fileName: String): String =
        File(fileName).readText(Charsets.UTF_8)


    companion object {
        private const val BASE_INPUT_DIR_PATH = "src/test/resources/tests/cppgen/input"
        private const val BASE_OUTPUT_DIR_PATH = "src/test/resources/tests/cppgen/output"

        @JvmStatic
        fun getZippedInputAndOutput(): Stream<Arguments> =
            getFilesSequence(BASE_INPUT_DIR_PATH)
                .zip(getFilesSequence(BASE_OUTPUT_DIR_PATH))
                .map {  (input, output) ->
                    Arguments.of(
                        input.toString(),
                        output.toString()
                    )
                }
                .asStream()
    }
}
