package inno.jago

import GoLexer
import GoParser
import inno.jago.converter.toSourceFileNode
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream

fun main() {
    // Init part
    val cs = CharStreams.fromFileName("sample.go")
    val lexer = GoLexer(cs)
    val stream = CommonTokenStream(lexer)
    val parser = GoParser(stream)

    parser.sourceFile().toSourceFileNode()

    if (parser.numberOfSyntaxErrors == 0) {
        println("The input GO program is syntactically correct")
    } else {
        println(
            "The input GO program has " + parser.numberOfSyntaxErrors + " syntax errors"
        )
    }
}
