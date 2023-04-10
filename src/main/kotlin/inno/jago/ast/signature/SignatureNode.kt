package inno.jago.ast.signature

import inno.jago.ast.type.TypeNode

sealed class SignatureNode<T>(
    val parameterNodes: List<ParameterNode>,
    val type: T
) {
    class TypedResultSignatureNode(
        parameterNodes: List<ParameterNode>,
        type: TypeNode
    ) : SignatureNode<TypeNode>(
        parameterNodes,
        type
    )

    class ParametrizedResultSignature(
        parameterNodes: List<ParameterNode>,
        type: List<ParameterNode>
    ) : SignatureNode<List<ParameterNode>>(
        parameterNodes,
        type
    )
}
