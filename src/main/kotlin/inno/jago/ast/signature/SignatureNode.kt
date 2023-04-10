package inno.jago.ast.signature

import inno.jago.ast.type.TypeNode

class SignatureNode(
    val parameterNodes: List<ParameterNode>,
    val type: List<TypeNode>
)
