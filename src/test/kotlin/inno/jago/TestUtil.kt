package inno.jago

import GoLexer
import GoParser
import inno.jago.ast.converter.toSourceFileNode
import inno.jago.ast.model.global.SourceFileNode
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.InputMismatchException
import kotlin.streams.asSequence

@Throws(InputMismatchException::class)
fun createAST(inputFilePath: String): SourceFileNode {
    val cs = CharStreams.fromFileName(inputFilePath)
    val lexer = GoLexer(cs)
    val tokenStream = CommonTokenStream(lexer)
    val parser = GoParser(tokenStream)
    val sourceFile = parser.sourceFile()
    if (parser.numberOfSyntaxErrors != 0) {
        throw InputMismatchException()
    }
    return sourceFile.toSourceFileNode()
}

fun getFilesSequence(path: String): Sequence<Path> =
    Files.list(Paths.get(path))
        .sorted()
        .asSequence()

