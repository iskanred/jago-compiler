package inno.jago.cppgen

import inno.jago.ast.model.decl.ConstDeclarationNode
import inno.jago.ast.model.decl.FunctionDeclarationNode
import inno.jago.ast.model.decl.VarDeclarationNode
import inno.jago.ast.model.global.SourceFileNode
import inno.jago.cppgen.declaration.translateToCode

/*
* Convert AST of Go code to C++ code
*/

class OutputProgram(
    val name: String,
    var file: String
) {
    fun addInstruction(instruction: String) {
        file = file + "\n" + instruction
    }
}

fun compile(root: SourceFileNode, name: String): OutputProgram {
    val program = OutputProgram(name, "")
    program.addInstruction(getIncludes())
    for (decl in root.topLevelDecls) {
        var instruction = ""
        instruction = when (decl) {
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
    return program
}

fun getIncludes(): String {
    return  "#include <iostream>\n" +
            "#include <string>\n" +
            "#include <vector>\n" +
            "#include <tuple>\n" +
            "\n" +
            "using namespace std;" +
            "\n"
}
