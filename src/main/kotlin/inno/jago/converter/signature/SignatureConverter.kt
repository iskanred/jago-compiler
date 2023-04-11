package inno.jago.converter.signature

import GoParser
import inno.jago.EntityNotSupported
import inno.jago.FunctionIdentifiersException
import inno.jago.ast.signature.ParameterNode
import inno.jago.ast.signature.SignatureNode
import inno.jago.ast.type.TypeNode
import inno.jago.converter.type.toTypeNode

fun GoParser.SignatureContext.toSignatureNode() = SignatureNode(
    parameterNodes = parameters().toParameterNodes(),
    resultNode = result().toResultNode()
)

fun GoParser.ParametersContext.toParameterNodes(): List<ParameterNode> {
    var haveIdentifiers = HaveIdentifiersState.START_STATE
    return parameterList().parameterDecl().flatMap { paramDecl ->
        // func (int, int, bool)
        if (paramDecl.identifierList() == null || paramDecl.identifierList().isEmpty) {
            if (haveIdentifiers == HaveIdentifiersState.HAVE_IDENTIFIERS) {
                throw FunctionIdentifiersException()
            }

            haveIdentifiers = HaveIdentifiersState.NOT_HAVE_IDENTIFIERS
            return@flatMap listOf(
                ParameterNode(
                    identifier = null,
                    type = paramDecl.type().toTypeNode()
                )
            )
        }

        // func (a, b int, c bool)
        if (haveIdentifiers == HaveIdentifiersState.NOT_HAVE_IDENTIFIERS) {
            throw FunctionIdentifiersException()
        }

        haveIdentifiers = HaveIdentifiersState.HAVE_IDENTIFIERS
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
                throw EntityNotSupported("Identifiers in result parameters")
            }
            return@map paramDecl.type().toTypeNode()
        }
    }

    return emptyList()
}

private enum class HaveIdentifiersState {
    START_STATE,
    HAVE_IDENTIFIERS,
    NOT_HAVE_IDENTIFIERS
}
