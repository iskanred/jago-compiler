package inno.jago.semantic.analyzer.signature

import inno.jago.ast.model.signature.ParameterNode
import inno.jago.ast.model.signature.SignatureNode
import inno.jago.semantic.model.ScopeNode
import inno.jago.semantic.model.Type
import inno.jago.semantic.model.VarEntity
import inno.jago.semantic.model.toType
import inno.jago.semantic.toType

fun SignatureNode.toType() = Type.FuncType(
    paramTypes = parameterNodes.map { it.type.toType() },
    returnType = resultNode.toType { it.toType() }
)

fun ParameterNode.toSemanticEntity(scope: ScopeNode) = VarEntity(
    type = type.toType(),
    identifier = identifier!!
).also {
    scope.addUniqueEntity(entity = it, pos = pos)
}
