package inno.jago.cppgen

import inno.jago.ast.model.decl.ConstDeclarationNode
import inno.jago.ast.model.decl.FunctionDeclarationNode
import inno.jago.ast.model.decl.VarDeclarationNode
import inno.jago.ast.model.global.SourceFileNode
import inno.jago.cppgen.declaration.translateToCode
import java.io.File

/*
* Convert AST of Go code to C++ code
*/

class Translator(
    var text: String
) {
    fun addInstruction(instruction: String) {
        text += "\n" + instruction
    }
}

fun compile(root: SourceFileNode): String {
    val program = Translator("")
    program.addInstruction(getIncludes())
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
        }
        program.addInstruction(instruction)
    }
    return program.text
}

fun createCppFile(fileName: String, text: String) = File(fileName).apply {
    if (exists()) {
        delete()
    }
    createNewFile()
    writeText(text)
}

fun getIncludes(): String = """
#include <iostream>
#include <string>
#include <vector>
#include <tuple>
using namespace std;

""".trimIndent()
