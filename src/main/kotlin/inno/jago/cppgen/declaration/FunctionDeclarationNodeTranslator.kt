package inno.jago.cppgen.declaration

import inno.jago.ast.model.decl.FunctionDeclarationNode
import inno.jago.ast.model.signature.ParameterNode
import inno.jago.ast.model.type.TypeNode
import inno.jago.cppgen.statement.translateToCode
import inno.jago.cppgen.type.translateToCode
import inno.jago.semantic.model.toType
import kotlin.random.Random

fun FunctionDeclarationNode.translateToCode(): String {
    val functionInstruction = StringBuilder()

    // return type
    val instructionOfReturnType = translateResultNodeToCode(this.signature.resultNode)
    functionInstruction.append(
        if (this.functionName == "main") {
            "int"
        } else {
            instructionOfReturnType
        }
    )

    // function name
    functionInstruction.append(" ${this.functionName}")

    // arguments
    val argumentsInstruction = "(${translateParameterNodesToCode(this.signature.parameterNodes)})"
    functionInstruction.append(argumentsInstruction)

    // function body
    val functionBody = this.functionBody.block
    functionInstruction.append("{")
    for (statement in functionBody) {
        functionInstruction.append("\n${statement.translateToCode()}")
    }
    functionInstruction.append("\n}")

    return functionInstruction.toString()
}

fun ParameterNode.translateToCode(): String {
    val instructionOfType = this.type.toType().translateToCode()
    if (this.identifier == null) {
        return instructionOfType + "nullArg_N" + "${Random.nextInt(1000000)}"
    }
    return instructionOfType + " " + this.identifier
}

fun translateResultNodeToCode(typeNodes: List<TypeNode>): String {
    val instructionOfReturnType = StringBuilder()
    if (typeNodes.isEmpty()) { // no return type
        instructionOfReturnType.append("void")
    } else if (typeNodes.size == 1) { // one return type
        instructionOfReturnType.append(typeNodes[0].translateToCode())
    } else { // more than one return type
        instructionOfReturnType.append("tuple<")
        instructionOfReturnType.append(typeNodes.joinToString { it.translateToCode() })
        instructionOfReturnType.append(">")
    }
    return instructionOfReturnType.toString()
}

fun translateParameterNodesToCode(parameterNodes: List<ParameterNode>): String {
    val instructionOfParameters = StringBuilder()
    for (i in parameterNodes.indices) {
        instructionOfParameters.append(parameterNodes[i].translateToCode())
        if (i != parameterNodes.size - 1) {
            instructionOfParameters.append(", ")
        }
    }
    return instructionOfParameters.toString()
}
