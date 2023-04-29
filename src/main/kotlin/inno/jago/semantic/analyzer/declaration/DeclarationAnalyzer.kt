package inno.jago.semantic.analyzer.declaration

import inno.jago.ast.model.decl.ConstDeclarationNode
import inno.jago.ast.model.decl.FunctionDeclarationNode
import inno.jago.ast.model.decl.TopLevelDeclNode
import inno.jago.ast.model.decl.VarDeclarationNode
import inno.jago.semantic.WrongTypeException
import inno.jago.semantic.analyzer.expression.toSemanticEntity
import inno.jago.semantic.analyzer.signature.toSemanticEntity
import inno.jago.semantic.analyzer.signature.toType
import inno.jago.semantic.analyzer.statement.toSemanticEntity
import inno.jago.semantic.model.EntityType
import inno.jago.semantic.model.ScopeNode
import inno.jago.semantic.model.SemanticEntity
import inno.jago.semantic.model.Type
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
    ).also { entity ->
        // add const to current scope
        scope.addUniqueEntity(entity)
    }
}

private fun VarDeclarationNode.toSemanticEntity(scope: ScopeNode): SemanticEntity {
    val entityType = if (expression == null && type == null) {
        TODO("throw exception here")
    } else if (expression == null && type != null) {
        val a = type.toType()
    } else if (expression != null && type == null) {
        val expressionEntity = expression.toSemanticEntity(scope)
        if (expressionEntity.type is Type.TupleType) {
            expressionEntity.type.elementTypes[positionInRow]
        } else {
            expressionEntity.type
        }
    } else {

    }

    val expressionEntity = expression?.toSemanticEntity(scope)
    val varExpressionType = if (expressionEntity.type is Type.TupleType) {
        expressionEntity.type.elementTypes[positionInRow]
    } else {
        expressionEntity.type
    }

    type?.toType()?.let { expectedType ->
        if (varExpressionType != expectedType) {
            throw WrongTypeException(expectedType = expectedType, actual = expressionEntity)
        }
    }

    return SemanticEntity(
        type = varExpressionType,
        pos = pos,
        entityType = EntityType.VARIABLE,
        identifier = identifier
    ).also { entity ->
        // add var to current scope
        scope.addUniqueEntity(entity)
    }
}

fun FunctionDeclarationNode.toSemanticEntity(scope: ScopeNode) = SemanticEntity(
    type = signature.toType(),
    pos = pos,
    entityType = EntityType.FUNCTION,
    identifier = functionName
).also { entity ->
    // add function to parent scope
    scope.addUniqueEntity(entity)
    // create scope for function
    val functionScope = scope.createNewScope("Function $functionName in [${scope.name}]")

    signature.parameterNodes.forEach { it.toSemanticEntity(functionScope) }
    functionBody.block.forEach { statementNode ->
        statementNode.toSemanticEntity(functionScope)
    }
}

