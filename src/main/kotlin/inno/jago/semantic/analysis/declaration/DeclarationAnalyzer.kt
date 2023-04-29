package inno.jago.semantic.analysis.declaration

import inno.jago.ast.model.decl.ConstDeclarationNode
import inno.jago.ast.model.decl.FunctionDeclarationNode
import inno.jago.ast.model.decl.TopLevelDeclNode
import inno.jago.ast.model.decl.VarDeclarationNode
import inno.jago.semantic.WrongTypeException
import inno.jago.semantic.analysis.expression.toSemanticEntity
import inno.jago.semantic.model.EntityType
import inno.jago.semantic.model.ScopeNode
import inno.jago.semantic.model.SemanticEntity
import inno.jago.semantic.model.toType

fun TopLevelDeclNode.toSemanticEntity(scope: ScopeNode): SemanticEntity = when(this) {
    is FunctionDeclarationNode -> toSemanticEntity(scope)
    is ConstDeclarationNode -> toSemanticEntity(scope)
    is VarDeclarationNode -> toSemanticEntity(scope)
}

private fun ConstDeclarationNode.toSemanticEntity(scope: ScopeNode): SemanticEntity {
    val expressionEntity = expression!!.toSemanticEntity(scope)
    type?.toType()?.let { expectedType ->
        if (expressionEntity.type != expectedType) {
            throw WrongTypeException(expectedType = expectedType, actual = expressionEntity)
        }
    }
    return SemanticEntity(
        type = expressionEntity.type,
        pos = pos,
        entityType = EntityType.CONSTANT,
        identifier = identifier
    )
}


private fun VarDeclarationNode.toSemanticEntity(scope: ScopeNode): SemanticEntity {
    TODO()
//    val expressionEntity = expression!!.toSemanticEntity(scope)
//    type?.toType()?.let { expectedType ->
//        if (expressionEntity.type != expectedType) {
//            throw WrongTypeException(expectedType = expectedType, actual = expressionEntity)
//        }
//    }
//    return SemanticEntity(
//        type = expressionEntity.type,
//        pos = pos,
//        entityType = EntityType.VARIABLE,
//        identifier = identifier
//    )
}
