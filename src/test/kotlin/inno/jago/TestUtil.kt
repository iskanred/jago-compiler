package inno.jago

import GoLexer
import GoParser
import inno.jago.ast.converter.toSourceFileNode
import inno.jago.ast.model.global.SourceFileNode
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import java.util.InputMismatchException

@Throws(InputMismatchException::class)
public fun createAST(inputFilePath: String): SourceFileNode {
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


