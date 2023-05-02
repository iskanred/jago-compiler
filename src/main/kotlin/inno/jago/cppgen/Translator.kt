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
        when (decl) {
            is FunctionDeclarationNode -> {
                var instruction = decl.translateToCode()
                program.AddInstruction("}")
            }

            is ConstDeclarationNode -> {
                program.AddInstruction("const ${decl.identifier} = ${decl.expression};")
            }

            is VarDeclarationNode -> {
                program.AddInstruction("auto ${decl.identifier} = ${decl.expression};")
            }
        }
    }
    return program
}
