package inno.jago.cppgen

import inno.jago.ast.model.decl.ConstDeclarationNode
import inno.jago.ast.model.decl.FunctionDeclarationNode
import inno.jago.ast.model.decl.VarDeclarationNode
import inno.jago.ast.model.global.SourceFileNode
import inno.jago.cppgen.declaration.translateToCode

/*
* Convert AST of Go code to C++ code
*/

class OutputProgram (
    val name: String,
    var file: String
) {
    fun AddInstruction(instruction: String) {
        file = file + "\n" + instruction
    }
}

fun compile(root: SourceFileNode, name: String) : OutputProgram {
    val program = OutputProgram(name, "")
    for (decl in root.topLevelDecls) {
        var instruction = ""
        when (decl) {
            is FunctionDeclarationNode -> {
                instruction = decl.translateToCode()
            }
            is ConstDeclarationNode -> {
                instruction = decl.translateToCode()
            }
            is VarDeclarationNode -> {
                instruction = decl.translateToCode()
            }
        }
        program.AddInstruction(instruction)
    }
    return program
}
