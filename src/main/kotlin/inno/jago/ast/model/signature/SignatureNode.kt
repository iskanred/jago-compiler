package inno.jago.ast.model.signature

import inno.jago.ast.model.type.TypeNode

class SignatureNode(
    val parameterNodes: List<ParameterNode>,
    val resultNode: List<TypeNode>
)
