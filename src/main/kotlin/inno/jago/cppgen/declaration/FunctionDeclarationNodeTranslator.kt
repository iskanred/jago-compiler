package inno.jago.cppgen.declaration

import inno.jago.ast.model.decl.FunctionDeclarationNode
import inno.jago.ast.model.signature.ParameterNode
import inno.jago.ast.model.type.TypeNode
import inno.jago.cppgen.statement.translateToCode
import inno.jago.cppgen.type.translateToCode
import inno.jago.semantic.model.toType

fun FunctionDeclarationNode.translateToCode(): String {
    var functionInstruction = ""

    // return type
    var instructionOfReturnType = translateResultNodeToCode(this.signature.resultNode)
    functionInstruction += instructionOfReturnType

    // function name
    functionInstruction += " " + this.functionName

    // arguments
    var argumentsInstruction = "(" + translateParameterNodesToCode(this.signature.parameterNodes) + ")"
    functionInstruction += argumentsInstruction

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
    if (this.identifier == null) {
        return instructionOfType + "nullArg"
    }
    return  instructionOfType + " " + this.identifier
}

fun translateResultNodeToCode(typeNodes: List<TypeNode>): String {
    var instructionOfReturnType = ""
    if (typeNodes.isEmpty()) { // no return type
        instructionOfReturnType = "void"
    } else if (typeNodes.size == 1) { // one return type
        instructionOfReturnType = typeNodes[0].translateToCode()
    } else { // more than one return type
        instructionOfReturnType = "tuple<"
        for (i in 0 until typeNodes.size) {
            instructionOfReturnType += typeNodes[i].translateToCode()
            if (i != typeNodes.size - 1) {
                instructionOfReturnType = "$instructionOfReturnType, "
            }
        }
    }
    return instructionOfReturnType
}

fun translateParameterNodesToCode(parameterNodes: List<ParameterNode>): String {
    var instructionOfParameters = ""
    for (i in 0 until parameterNodes.size) {
        instructionOfParameters += parameterNodes[i].translateToCode()
        if (i != parameterNodes.size - 1) {
            instructionOfParameters = "$instructionOfParameters, "
        }
    }
    return instructionOfParameters
}
