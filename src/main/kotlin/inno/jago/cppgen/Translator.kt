package inno.jago.cppgen

import inno.jago.ast.model.decl.ConstDeclarationNode
import inno.jago.ast.model.decl.FunctionDeclarationNode
import inno.jago.ast.model.decl.StructDeclarationNode
import inno.jago.ast.model.decl.VarDeclarationNode
import inno.jago.ast.model.global.SourceFileNode
import inno.jago.cppgen.declaration.translateToCode
import kotlin.text.StringBuilder

/**
 * Convert AST of Go code to C++ code
 */
class Translator(val root: SourceFileNode) {
    fun translate(): String {
        val resultText = StringBuilder()
        resultText.appendInstruction(instructionText = getIncludes())

        for (decl in root.topLevelDecls) {
            val instruction= when (decl) {
                is FunctionDeclarationNode -> {
                    decl.translateToCode()
                }
                is ConstDeclarationNode -> {
                    decl.translateToCode()
                }
                is VarDeclarationNode -> {
                    decl.translateToCode()
                }
                is StructDeclarationNode -> {
                    decl.translateToCode()
                }
            }
            resultText.appendInstruction(instructionText = instruction)
        }

        return resultText.toString()
    }

    private fun getIncludes(): String = """
        #include <iostream>
        #include <string>
        #include <vector>
        #include <tuple>
        using namespace std;
    """.trimIndent() + "\n"

    private fun StringBuilder.appendInstruction(instructionText: String) =
        append("\n$instructionText")
}
