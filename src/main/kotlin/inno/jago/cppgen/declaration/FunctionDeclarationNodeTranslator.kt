package inno.jago.cppgen.declaration

import inno.jago.ast.model.decl.FunctionDeclarationNode
import inno.jago.ast.model.signature.ParameterNode
import inno.jago.ast.model.type.TypeNode
import inno.jago.cppgen.statement.translateToCode
import inno.jago.cppgen.type.translateToCode
import inno.jago.semantic.model.toType
import kotlin.random.Random

fun FunctionDeclarationNode.translateToCode(): String {
    var functionInstruction = ""

    // return type
    val instructionOfReturnType = translateResultNodeToCode(this.signature.resultNode)
    functionInstruction += if (this.functionName == "main") {
        "int"
    } else {
        instructionOfReturnType
    }

    // function name
    functionInstruction += " " + this.functionName

    // arguments
    val argumentsInstruction = "(" + translateParameterNodesToCode(this.signature.parameterNodes) + ")"
    functionInstruction += argumentsInstruction

    // function body
    val functionBody = this.functionBody.block
    functionInstruction += "{"
    for (statement in functionBody) {
        functionInstruction += "\n"+ statement.translateToCode()
    }
    functionInstruction += "\n}"

    return functionInstruction
}

fun ParameterNode.translateToCode(): String {
    val instructionOfType = this.type.toType().translateToCode()
    if (this.identifier == null) {
        return instructionOfType + "nullArg_N" + "${Random.nextInt(1000000)}"
    }
    return  instructionOfType + " " + this.identifier
}

fun translateResultNodeToCode(typeNodes: List<TypeNode>): String {
    var instructionOfReturnType: String
    if (typeNodes.isEmpty()) { // no return type
        instructionOfReturnType = "void"
    } else if (typeNodes.size == 1) { // one return type
        instructionOfReturnType = typeNodes[0].translateToCode()
    } else { // more than one return type
        instructionOfReturnType = "tuple<"
        instructionOfReturnType += typeNodes.joinToString { it.translateToCode() }
//        for (i in typeNodes.indices) {
//            instructionOfReturnType += typeNodes[i].translateToCode()
//            if (i != typeNodes.size - 1) {
//                instructionOfReturnType = "$instructionOfReturnType, "
//            }
//        }
        instructionOfReturnType += ">"
    }
    return instructionOfReturnType
}

fun translateParameterNodesToCode(parameterNodes: List<ParameterNode>): String {
    var instructionOfParameters = ""
    for (i in parameterNodes.indices) {
        instructionOfParameters += parameterNodes[i].translateToCode()
        if (i != parameterNodes.size - 1) {
            instructionOfParameters = "$instructionOfParameters, "
        }
    }
    return instructionOfParameters
}
