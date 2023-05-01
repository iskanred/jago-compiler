package inno.jago.semantic.analyzer.statement

import inno.jago.ast.model.statement.ShortVarDeclNode
import inno.jago.common.JaGoException
import inno.jago.common.UnreachableCodeException
import inno.jago.common.WrongNumberOfExpressionsException
import inno.jago.semantic.WrongTypeException
import inno.jago.semantic.model.EntityType
import inno.jago.semantic.model.ScopeNode
import inno.jago.semantic.model.Type
import inno.jago.semantic.model.SemanticEntity

// ShortVarDecl = IdentifierList ":=" ExpressionList
fun ShortVarDeclNode.toSemanticEntity(scope: ScopeNode): SemanticEntity {
    val semanticEntities = expression.map { toSemanticEntity(scope) }
    if (semanticEntities.any { it.type is Type.TupleType } ) { // a, b := someFun(), someFun return > 1 elements
        if (semanticEntities.size != 1) {
            throw JaGoException(msg = "Can be only one tuple in expressions")
        }

        val tupleEntity = semanticEntities.first()
        if (tupleEntity.type !is Type.TupleType) {
            throw UnreachableCodeException()
        }

        val tupleElements = tupleEntity.type.elementTypes
        if (identifierList.size != tupleElements.size) {
            throw WrongNumberOfExpressionsException(
                expected =  identifierList.size,
                actual = tupleElements.size,
                pos = pos
            )
        }

        identifierList.zip(tupleElements).forEach {
            val visibleEntity = scope.findVisibleEntity(it.first)
            if (visibleEntity == null) {
                scope.addUniqueEntity(SemanticEntity(
                    type = it.second,
                    pos = pos,
                    entityType = EntityType.VAR,
                    identifier = it.first
                ))
                return@forEach
            }

            if (visibleEntity.type != it.second) {
                throw WrongTypeException(it.second, actual = visibleEntity)
            }
        }
    }
    else { // a, b := 3, true. No tuple in semanticEntities.
        if (semanticEntities.size != identifierList.size) {
            throw WrongNumberOfExpressionsException(
                expected =  identifierList.size,
                actual = semanticEntities.size,
                pos = pos
            )
        }

        identifierList.zip(semanticEntities).forEach {
            val visibleEntity = scope.findVisibleEntity(it.first)
            if (visibleEntity == null) {
                scope.addUniqueEntity(SemanticEntity(
                    type = it.second.type,
                    pos = pos,
                    entityType = EntityType.VAR,
                    identifier = it.first
                ))
                return@forEach
            }

            if (visibleEntity.type != it.second.type) {
                throw WrongTypeException(it.second.type, actual = visibleEntity)
            }
        }
    }

    return SemanticEntity(
        type = Type.UnitType,
        pos = pos,
        entityType = EntityType.NO_IDENTIFIER
    )
}
