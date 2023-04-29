package inno.jago.semantic.analyzer.signature

import inno.jago.ast.model.signature.ParameterNode
import inno.jago.ast.model.signature.SignatureNode
import inno.jago.semantic.model.EntityType
import inno.jago.semantic.model.ScopeNode
import inno.jago.semantic.model.SemanticEntity
import inno.jago.semantic.model.Type
import inno.jago.semantic.model.toType
import inno.jago.semantic.toType

fun SignatureNode.toType() = Type.FuncType(
    paramTypes = parameterNodes.map { it.type.toType() },
    returnType = resultNode.toType { it.toType() }
)

fun ParameterNode.toSemanticEntity(scope: ScopeNode) = SemanticEntity(
    type = type.toType(),
    pos = pos,
    entityType = EntityType.PARAMETER,
    identifier = identifier
).also { entity ->
    // add parameter to parent scope
    scope.addUniqueEntity(entity)
}
