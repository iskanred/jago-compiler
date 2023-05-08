package inno.jago.translator.cpp

import GoLexer
import GoParser
import inno.jago.ast.converter.toSourceFileNode
import inno.jago.cppgen.Translator
import inno.jago.cppgen.compile
import inno.jago.semantic.TypeChecker
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.stream.Stream
import kotlin.test.assertEquals

class TranslatorToCppTest {

    @ParameterizedTest
    @MethodSource("inno.jago.translator.cpp.TranslatorToCppTest#getZippedInputAndOutput")
    fun `hello world`(input: String, output: String) {
        val expected = readFileDirectlyAsText(output)

        val cs = CharStreams.fromFileName(input)
        val lexer = GoLexer(cs)
        val stream = CommonTokenStream(lexer)
        val parser = GoParser(stream)

        @Suppress("UNUSED_VARIABLE") val sourceFileNode = parser.sourceFile().toSourceFileNode()
        TypeChecker(sourceFileNode = sourceFileNode).startTypeCheck()

        val got = compile(root = sourceFileNode)

        assertEquals(expected, got)
    }

    private fun readFileDirectlyAsText(fileName: String): String = File(fileName).readText(Charsets.UTF_8)


    companion object {
        private const val BASE_INPUT_DIR = "src/test/resources/tests/translator/input/for"
        private const val BASE_OUTPUT_DIR = "src/test/resources/tests/translator/output/for"

        @JvmStatic
        fun pathInputStream(): Stream<Path> = getFilesStream(BASE_INPUT_DIR)

        @JvmStatic
        fun pathOutputStream(): Stream<Path> = getFilesStream(BASE_OUTPUT_DIR)

        private fun getFilesStream(path: String) = Files.list(Paths.get(path)).sorted()

        @JvmStatic
        private fun getZippedInputAndOutput(): Stream<Arguments> {
            return Stream.of(
                *(pathInputStream().toArray().zip(pathOutputStream().toArray())
                    .map { Arguments.of(it.first.toString(), it.second.toString()) }.toTypedArray())
            )
        }
    }
}
