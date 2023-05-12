package inno.jago.semantic.analyzer.declaration

import inno.jago.ast.UnknownTypeException
import inno.jago.ast.model.decl.ConstDeclarationNode
import inno.jago.ast.model.decl.FunctionDeclarationNode
import inno.jago.ast.model.decl.StructDeclarationNode
import inno.jago.ast.model.decl.TopLevelDeclNode
import inno.jago.ast.model.decl.VarDeclarationNode
import inno.jago.common.WrongNumberOfExpressionsException
import inno.jago.semantic.VarDeclMustPresentTypeOrExpressionException
import inno.jago.semantic.WrongTypeException
import inno.jago.semantic.analyzer.expression.toSemanticEntity
import inno.jago.semantic.analyzer.statement.toSemanticEntity
import inno.jago.semantic.analyzer.signature.toSemanticEntity
import inno.jago.semantic.analyzer.signature.toType
import inno.jago.semantic.model.ConstEntity
import inno.jago.semantic.model.FuncEntity
import inno.jago.semantic.model.NamedEntity
import inno.jago.semantic.model.ScopeNode
import inno.jago.semantic.model.StructEntity
import inno.jago.semantic.model.Type
import inno.jago.semantic.model.VarEntity
import inno.jago.semantic.model.toType

fun TopLevelDeclNode.toSemanticEntity(scope: ScopeNode): NamedEntity = when (this) {
    is FunctionDeclarationNode -> toSemanticEntity(scope)
    is ConstDeclarationNode -> toSemanticEntity(scope)
    is VarDeclarationNode -> toSemanticEntity(scope)
    is StructDeclarationNode -> toSemanticEntity(scope)
}

private fun ConstDeclarationNode.toSemanticEntity(scope: ScopeNode): ConstEntity {
    val expressionEntity = expression!!.toSemanticEntity(scope)

    type?.toType()?.let { expectedType ->
        if (expressionEntity.type != expectedType) {
            throw WrongTypeException(expectedType, actualType = expressionEntity.type, pos = pos)
        }
    }

    return ConstEntity(type = expressionEntity.type, identifier = identifier).also {
        scope.addUniqueEntity(entity = it, pos = pos)
    }
}

@Suppress("ThrowsCount")
private fun VarDeclarationNode.toSemanticEntity(scope: ScopeNode): VarEntity {
    val entityType = if (expression == null && type == null) {
        throw VarDeclMustPresentTypeOrExpressionException(varIdentifier = identifier, pos = pos)
    } else if (expression == null && type != null) {
        val type = type.toType()
        if (type is Type.NamedType) {
            val structEntity = scope.findVisibleStructEntities(type.name)
            structEntity?.type ?: throw UnknownTypeException(pos = pos, entityName = type.name)
        } else {
            type
        }
    } else { // expression != null
        val expressionEntity = expression!!.toSemanticEntity(scope)
        val expressionType = if (expressionEntity.type is Type.TupleType) {
            /* == Проверка кол-ва переменных слева и выражений для присвоения справа == */
            if (expressionEntity.type is Type.NamedType && scope.findVisibleStructEntities(expressionEntity.type.name) == null) {
                throw UnknownTypeException(pos = pos, entityName = expressionEntity.type.name)
            }
            if (numberOfDeclarationsInRow != expressionEntity.type.elementTypes.size) {
                throw WrongNumberOfExpressionsException(
                    expected = numberOfDeclarationsInRow,
                    actual = expressionEntity.type.elementTypes.size,
                    pos = pos
                )
            }
            /* ======================================================================== */
            expressionEntity.type.elementTypes[positionInRow]
        } else if (positionInRow == -1) {
            expressionEntity.type
        } else {
            throw WrongNumberOfExpressionsException(
                expected = numberOfDeclarationsInRow,
                actual = 1,
                pos = pos
            )
        }

        type?.toType()?.let { expectedType ->
            if (expressionType != expectedType) {
                throw WrongTypeException(expectedType, actualType = expressionEntity.type, pos = pos)
            }
        }

        expressionType
    }

    return VarEntity(type = entityType, identifier = identifier).also {
        scope.addUniqueEntity(entity = it, pos = pos)
    }
}

fun FunctionDeclarationNode.toSemanticEntity(scope: ScopeNode) = FuncEntity(
    type = signature.toType(),
    identifier = functionName
).also { entity ->
    // add function to parent scope
    scope.addUniqueEntity(entity = entity, pos = pos)

    // create scope for function
    val functionScope = scope.createNewFuncScope(
        functionName = functionName,
        expectedReturnType = (entity.type as Type.FuncType).returnType
    )

    // analyze parameters
    signature.parameterNodes.forEach { paramNode ->
        paramNode.toSemanticEntity(functionScope)
    }

    functionBody.block.forEach { statementNode ->
        statementNode.toSemanticEntity(functionScope)
    }
}

fun StructDeclarationNode.toSemanticEntity(scope: ScopeNode) = StructEntity(
    identifier = identifier,
    type = type!!.toType() as Type.StructType
).also { entity ->
    (entity.type as Type.StructType).fields.forEach { (_, value) ->
        if (value is Type.NamedType && scope.findVisibleStructEntities(value.name) == null) {
            throw UnknownTypeException(pos = pos, entityName = value.name)
        }
    }
    scope.addUniqueEntity(entity = entity, pos = pos)
}

