package inno.jago.codogen_to_cpp.declaration

import inno.jago.ast.model.decl.FunctionDeclarationNode
import inno.jago.ast.model.signature.ParameterNode
import inno.jago.codogen_to_cpp.statement.translateToCode
import inno.jago.codogen_to_cpp.type.translateToCode
import inno.jago.semantic.model.toType

fun FunctionDeclarationNode.translateToCode(): String {
    var functionInstruction = ""
    // return type

    var returnType = this.signature.resultNode
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
    functionInstruction = functionInstruction + " " + this.functionName + "("

    // arguments
    var argumentsInstruction = ""
    var arguments = this.signature.parameterNodes
    for (i in 0 until arguments.size) {
        argumentsInstruction += arguments[i].translateToCode()
        if (i != arguments.size - 1) {
            argumentsInstruction = "$argumentsInstruction, "
        }
    }
    functionInstruction = "$functionInstruction$argumentsInstruction) {"

    // function body

    var functionBody = this.functionBody.block
    for (statement in functionBody) {
        functionInstruction += statement.translateToCode()
    }



    return functionInstruction
}

fun ParameterNode.translateToCode(): String {
    var instruction = ""
    var instructionOfType = this.type.toType().translateToCode()
    instruction = instruction + instructionOfType + " " + this.identifier
    return instruction
}
