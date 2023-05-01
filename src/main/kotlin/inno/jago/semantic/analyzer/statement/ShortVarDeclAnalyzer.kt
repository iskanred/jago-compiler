package inno.jago.semantic.analyzer.statement

import inno.jago.ast.model.statement.ShortVarDeclNode
import inno.jago.common.JaGoException
import inno.jago.common.UnreachableCodeException
import inno.jago.common.WrongNumberOfExpressionsException
import inno.jago.semantic.model.EntityType
import inno.jago.semantic.model.ScopeNode
import inno.jago.semantic.model.Type
import inno.jago.semantic.model.SemanticEntity

// ShortVarDecl = IdentifierList ":=" ExpressionList
@Suppress("ThrowsCount")
fun ShortVarDeclNode.toSemanticEntity(scope: ScopeNode): SemanticEntity {
    val semanticEntities = expression.map { toSemanticEntity(scope) }
    if (semanticEntities.any { it.type is Type.FuncType } ) {
        if (semanticEntities.size != 1) {
            throw JaGoException(msg = "Can be only one function in expressions")
        }

        val funcEntity = semanticEntities.first()
        if (funcEntity.type !is Type.FuncType) {
            throw UnreachableCodeException()
        }

        val returnType = funcEntity.type.returnType
        if (returnType is Type.TupleType) {
            if (identifierList.size != returnType.elementTypes.size) {
                throw WrongNumberOfExpressionsException(
                    expected =  identifierList.size,
                    actual = returnType.elementTypes.size,
                    pos = pos
                )
            }

        } else if (returnType is Type.UnitType) {
            throw JaGoException("Function return nothing in short var decl")
        } else {
            if (identifierList.size != 1) {
                throw WrongNumberOfExpressionsException(
                    expected = identifierList.size,
                    actual = 1,
                    pos = pos
                )
            }

            val entity = SemanticEntity(
                type = returnType, pos = pos,
                entityType = EntityType.VAR, identifier = identifierList.first()
            )

            scope.addUniqueEntity(entity)
            return entity
        }
    }
    else {
        if (semanticEntities.size != identifierList.size) {
            throw TODO()
        }

    }

}
