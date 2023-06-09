package inno.jago

import GoLexer
import GoParser
import com.andreapivetta.kolor.red
import inno.jago.ast.converter.toSourceFileNode
import inno.jago.codegen.GoCodegen
import inno.jago.common.InputArgumentsException
import inno.jago.cppgen.Translator
import inno.jago.semantic.TypeChecker
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import java.io.File

@Suppress("ThrowsCount")
private fun validateAndGetCliArguments(args: Array<String>): Pair<String, String> {
    if (args.isEmpty()) {
        throw InputArgumentsException("Please, provide an input file to be compiled!")
    }
    if (args.size > 3 || args.size == 2) {
        throw InputArgumentsException("""
            Please, provide only these arguments:
            - input filename (required)
            - flag "-o" (optional)
            - output filename (optional but together with flag only)
        """.trimIndent())
    }

    val inputFilename = args[0]
    val outputFilename = if (args.size == 1) {
        "output.cpp"
    } else {
        if (args[1] != "-o") {
            throw InputArgumentsException("Flag must be \"-o\"")
        } else {
            args[2]
        }
    }

    return inputFilename to outputFilename
}

private fun createTextFile(fileName: String, content: String) = File(fileName).apply {
    if (exists()) {
        delete()
    }
    createNewFile()
    writeText(content)
}

private fun createBinaryFile(fileName: String, content: ByteArray) = File(fileName).apply {
    if (exists()) {
        delete()
    }
    createNewFile()
    writeBytes(content)
}

fun main(args: Array<String>) {
    runCatching {
        val (inputFilename, outputFilename) = validateAndGetCliArguments(args)

        // Init part
        val cs = CharStreams.fromFileName(inputFilename)
        val lexer = GoLexer(cs)
        val stream = CommonTokenStream(lexer)
        val parser = GoParser(stream)

        @Suppress("UNUSED_VARIABLE")
        val sourceFileNode = parser.sourceFile().toSourceFileNode()
        TypeChecker(sourceFileNode = sourceFileNode).startTypeCheck()

        val compiler = GoCodegen()
        createBinaryFile(
            fileName = "Main.class",
            content = compiler.compile(sourceFileNode, "Main")
        )

        val outputCppCode = Translator(root = sourceFileNode).translate()
        createTextFile(outputFilename, outputCppCode)

        Runtime.getRuntime().exec("g++ $outputFilename -o $outputFilename-exe")
    }.onFailure {
        println(it.message!!.red())
    }
}
