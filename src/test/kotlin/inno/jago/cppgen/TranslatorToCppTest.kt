package inno.jago.cppgen

import GoLexer
import GoParser
import inno.jago.ast.converter.toSourceFileNode
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
import kotlin.streams.asSequence
import kotlin.streams.asStream
import kotlin.test.assertEquals

class TranslatorToCppTest {

    @ParameterizedTest
    @MethodSource("inno.jago.cppgen.TranslatorToCppTest#getZippedInputAndOutput")
    fun test(input: String, output: String) {
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

    private fun readFileDirectlyAsText(fileName: String): String =
        File(fileName).readText(Charsets.UTF_8)


    companion object {
        private const val BASE_INPUT_DIR = "src/test/resources/tests/translator/input/for"
        private const val BASE_OUTPUT_DIR = "src/test/resources/tests/translator/output/for"

        @JvmStatic
        fun pathInputSequence(): Sequence<Path> = getFilesSequence(BASE_INPUT_DIR)

        @JvmStatic
        fun pathOutputSequence(): Sequence<Path> = getFilesSequence(BASE_OUTPUT_DIR)

        private fun getFilesSequence(path: String): Sequence<Path> =
            Files.list(Paths.get(path))
                .sorted()
                .asSequence()

        @JvmStatic
        fun getZippedInputAndOutput(): Stream<Arguments> =
            pathInputSequence()
                .zip(pathOutputSequence())
                .map {  (input, output) ->
                    Arguments.of(
                        input.toString(),
                        output.toString()
                    )
                }
                .asStream()
    }
}
