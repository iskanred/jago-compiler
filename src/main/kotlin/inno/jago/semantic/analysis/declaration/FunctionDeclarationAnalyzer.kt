package inno.jago.semantic.analysis.declaration

import inno.jago.ast.model.decl.FunctionDeclarationNode
import inno.jago.ast.model.signature.ParameterNode
import inno.jago.semantic.analysis.statement.toSemanticEntity
import inno.jago.semantic.model.EntityType
import inno.jago.semantic.model.ScopeNode
import inno.jago.semantic.model.SemanticEntity
import inno.jago.semantic.model.Type
import inno.jago.semantic.model.toType

fun FunctionDeclarationNode.toSemanticEntity(scope: ScopeNode): SemanticEntity {
    val currentScope = scope.createNewScope("Function $functionName in ${scope.name}")
    currentScope.addUniqueEntities(signature.parameterNodes) { it.toSemanticEntity(scope) }

    functionBody.block.forEach {
        it.toSemanticEntity(scope)
    }

    return SemanticEntity(
        type = Type.Func(signature.parameterNodes.map { it.type.toType() }, signature.resultNode.map { it.toType() }),
        pos = pos,
        entityType = EntityType.FUNCTION,
        identifier = functionName
    )
}

private fun ParameterNode.toSemanticEntity(scope: ScopeNode): SemanticEntity {
    return SemanticEntity(
        type = type.toType(),
        pos = pos,
        entityType =  EntityType.PARAMETER,
        identifier = identifier
    )
}



