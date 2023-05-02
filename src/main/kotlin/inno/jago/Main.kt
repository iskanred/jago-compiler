package inno.jago

import GoLexer
import GoParser
import inno.jago.ast.converter.toSourceFileNode
import inno.jago.codegen.GoCodegen
import inno.jago.semantic.TypeChecker
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream

fun main() {
    // Init part
    val cs = CharStreams.fromFileName("sample.go")
    val lexer = GoLexer(cs)
    val stream = CommonTokenStream(lexer)
    val parser = GoParser(stream)
    val codegen = GoCodegen()

    @Suppress("UNUSED_VARIABLE")
    val sourceFileNode = parser.sourceFile().toSourceFileNode()
    TypeChecker(sourceFileNode = sourceFileNode).startTypeCheck()

    if (parser.numberOfSyntaxErrors == 0) {
        println("The input GO program is syntactically correct")
    } else {
        println(
            "The input GO program has " + parser.numberOfSyntaxErrors + " syntax errors"
        )
    }

//    codegen.compile(sourceFileNode, "123")
}
