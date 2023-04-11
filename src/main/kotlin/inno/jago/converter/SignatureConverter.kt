package inno.jago.converter

import GoParser
import inno.jago.NotSupported
import inno.jago.ast.signature.ParameterNode
import inno.jago.ast.signature.SignatureNode
import inno.jago.ast.type.TypeNode

fun GoParser.SignatureContext.toSignatureNode() = SignatureNode(
    parameterNodes = parameters().toParameterNodes(),
    resultNode = result().toResultNode()
)

fun GoParser.ParametersContext.toParameterNodes(): List<ParameterNode> {
    var haveIdentifiers = 0
    return parameterList().parameterDecl().flatMap { paramDecl ->
        // func (int, int, bool)
        if (paramDecl.identifierList() == null || paramDecl.identifierList().isEmpty) {
            if (haveIdentifiers == 1) {
                throw NotSupported("Искан добавь")
            }

            haveIdentifiers = -1
            return@flatMap listOf(
                ParameterNode(
                    identifier = null,
                    type = paramDecl.type().toTypeNode()
                )
            )
        }

        // func (a, b int, c bool)
        if (haveIdentifiers == -1) {
            throw NotSupported("Искан добавь")
        }

        haveIdentifiers = 1
        return@flatMap paramDecl.identifierList().IDENTIFIER().map { ident ->
            return@map ParameterNode(
                identifier = ident.text,
                type = paramDecl.type().toTypeNode()
            )
        }
    }
}

fun GoParser.ResultContext.toResultNode(): List<TypeNode> {
    type()?.let {
        return listOf(it.toTypeNode())
    }

    parameters()?.parameterList()?.let { params ->
        return@let params.parameterDecl().map { paramDecl ->
            if (paramDecl.identifierList() != null || paramDecl.identifierList().IDENTIFIER().isNotEmpty()) {
                throw NotSupported(reason = "Identifiers in result parameters")
            }

            return@map paramDecl.type().toTypeNode()
        }
    }

    return emptyList()
}
