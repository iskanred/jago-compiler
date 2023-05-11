package inno.jago.ast.converter.signature

import GoParser
import inno.jago.ast.FunctionIdentifiersException
import inno.jago.ast.converter.common.toPos
import inno.jago.ast.model.signature.ParameterNode
import inno.jago.ast.model.signature.SignatureNode
import inno.jago.ast.model.type.TypeNode
import inno.jago.ast.converter.type.toTypeNode
import inno.jago.common.EntityNotSupportedException

fun GoParser.SignatureContext.toSignatureNode() = SignatureNode(
    parameterNodes = parameters().toParameterNodes(),
    resultNode = result()?.toResultNode() ?: emptyList()
)

fun GoParser.ParametersContext.toParameterNodes(): List<ParameterNode> {
    var haveIdentifiers = HaveIdentifiersState.START_STATE
    return parameterList()?.parameterDecl()?.flatMap { paramDecl ->
        // func (int, int, bool)
        if (paramDecl.identifierList() == null || paramDecl.identifierList().isEmpty) {
            if (haveIdentifiers == HaveIdentifiersState.HAVE_IDENTIFIERS) {
                throw FunctionIdentifiersException(pos = toPos())
            }

            haveIdentifiers = HaveIdentifiersState.NOT_HAVE_IDENTIFIERS
            return@flatMap listOf(
                ParameterNode(
                    toPos(),
                    identifier = null,
                    type = paramDecl.type().toTypeNode()
                )
            )
        }

        // func (a, b int, c bool)
        if (haveIdentifiers == HaveIdentifiersState.NOT_HAVE_IDENTIFIERS) {
            throw FunctionIdentifiersException(pos = toPos())
        }

        haveIdentifiers = HaveIdentifiersState.HAVE_IDENTIFIERS
        return@flatMap paramDecl.identifierList().IDENTIFIER().map { ident ->
            return@map ParameterNode(
                toPos(),
                identifier = ident.text,
                type = paramDecl.type().toTypeNode()
            )
        }
    } ?: emptyList()
}

@Suppress("ReturnCount")
fun GoParser.ResultContext.toResultNode(): List<TypeNode> {
    type()?.let {
        return listOf(it.toTypeNode())
    }

    parameters()?.parameterList()?.let { params ->
        return params.parameterDecl().map { paramDecl ->
            if (paramDecl.identifierList() != null && paramDecl.identifierList().IDENTIFIER().isNotEmpty()) {
                throw EntityNotSupportedException("Identifiers in result parameters")
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
