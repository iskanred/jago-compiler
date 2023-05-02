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
    program.AddInstruction(getIncludes())
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
        program.AddInstruction(instruction)
    }
    return program
}

fun getIncludes(): String{
    return "#include <stdio.h>\n" +
            "#include <cstdio>\n" +
            "#include <algorithm>\n" +
            "#include <iostream>\n" +
            "#include <string>\n" +
            "#include <vector>\n" +
            "#include <queue>\n" +
            "#include <stack>\n" +
            "#include <math.h>\n" +
            "#include <cmath>\n" +
            "#include <map>\n" +
            "#include <set>\n" +
            "#include <cstdlib>\n" +
            "#include <utility>\n" +
            "#include <iomanip>\n" +
            "#include <cstring>\n" +
            "\n" +
            "using namespace std;"
}
