package inno.jago.converter

import GoParser
import inno.jago.ast.signature.ParameterNode
import inno.jago.ast.signature.SignatureNode
import inno.jago.ast.type.TypeNode


fun GoParser.SignatureContext.toSignatureNode() = SignatureNode(
    parameterNodes = parameters().toParameterNodes(),
    type = TODO(),
)

fun GoParser.ParametersContext.toParameterNodes(): List<ParameterNode> {
    parameterList().parameterDecl().forEach { paramDecl ->
        paramDecl
    }
}
