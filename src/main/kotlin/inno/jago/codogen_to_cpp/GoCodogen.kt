package inno.jago.codogen_to_cpp

import inno.jago.ast.model.decl.ConstDeclarationNode
import inno.jago.ast.model.decl.FunctionDeclarationNode
import inno.jago.ast.model.decl.VarDeclarationNode
import inno.jago.ast.model.global.SourceFileNode
import inno.jago.ast.model.signature.ParameterNode
import inno.jago.semantic.model.Type
import inno.jago.semantic.model.toType

/*
* Convert AST of Go code to C++ code
* */

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
                var instruction = getFunctionInstruction(decl)
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

fun getFunctionInstruction(functionDeclarationNode: FunctionDeclarationNode): String {
    var functionInstruction = ""
    // return type

    var returnType = functionDeclarationNode.signature.resultNode
    var instructionOfReturnType = ""
    if (returnType.isEmpty()) {
        instructionOfReturnType = "void"
    } else if (returnType.size == 1) {
        instructionOfReturnType = returnType[0].toString()
    } else {
        instructionOfReturnType = "tuple<"
        for (i in 0..returnType.size - 1) {
            instructionOfReturnType = instructionOfReturnType + returnType[i].toString()
            if (i != returnType.size - 1) {
                instructionOfReturnType = instructionOfReturnType + ", "
            }
        }
        instructionOfReturnType = instructionOfReturnType + ">"
    }
    functionInstruction = functionInstruction + instructionOfReturnType

    // function name
    functionInstruction = functionInstruction + " " + functionDeclarationNode.functionName + "("

    // arguments
    var argumentsInstruction = ""
    var arguments = functionDeclarationNode.signature.parameterNodes
    for (i in 0..arguments.size - 1) {
        argumentsInstruction = argumentsInstruction + getArgumentInstruction(arguments[i])
        if (i != arguments.size - 1) {
            argumentsInstruction = argumentsInstruction + ", "
        }
    }
    functionInstruction = functionInstruction + argumentsInstruction + ") {"

    // function body

    var functionBody = functionDeclarationNode.functionBody



    return functionInstruction
}

fun getArgumentInstruction(argument: ParameterNode): String {
    var instruction = ""
    var type = argument.type
    var instructionOfType = getTypeInstruction(argument.type.toType())
    instruction = instruction + instructionOfType + " " + argument.identifier
    return instruction
}

fun getTypeInstruction(type: Type): String{
    when (type){
        is Type.IntegerType -> {
            return "int"
        }
        is Type.DoubleType -> {
            return "double"
        }
        is Type.StringType -> {
            return "string"
        }
        is Type.BoolType -> {
            return "bool"
        }
        is Type.ArrayType -> {
            return "vector<${getTypeInstruction(type.elementType)}>"
        }
        is Type.PointerType -> {
            return "*${getTypeInstruction(type.baseType)}"
        }
        is Type.TupleType -> {
            var types = ""
            for (i in 0..type.elementTypes.size - 1) {
                types = types + getTypeInstruction(type.elementTypes[i])
                if (i != type.elementTypes.size - 1) {
                    types = types + ", "
                }
            }
            return "tuple<${types}>"
        }
        is Type.FuncType -> {
            return "auto"
        }
        else -> {
            return "void"
        }

    }
}
