package inno.jago.semantic.analyzer.statement

import inno.jago.ast.model.statement.ShortVarDeclNode
import inno.jago.common.JaGoException
import inno.jago.common.UnreachableCodeException
import inno.jago.common.WrongNumberOfExpressionsException
import inno.jago.semantic.WrongTypeException
import inno.jago.semantic.analyzer.expression.toSemanticEntity
import inno.jago.semantic.model.ScopeNode
import inno.jago.semantic.model.StatementEntity
import inno.jago.semantic.model.Type
import inno.jago.semantic.model.VarEntity

// ShortVarDecl = IdentifierList ":=" ExpressionList
@Suppress("LongMethod", "ThrowsCount")
fun ShortVarDeclNode.toSemanticEntity(scope: ScopeNode): StatementEntity {
    val semanticEntities = expression.map { it.toSemanticEntity(scope) }

    // a, b := someFun(); someFun return > 1 elements
    if (semanticEntities.any { it.type is Type.TupleType } ) {
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

        identifierList.zip(tupleElements).forEach { (identifier, type) ->
            val visibleEntity = scope.findVisibleObjectEntity(identifier)
            if (visibleEntity == null) {
                scope.addUniqueEntity(
                    entity = VarEntity(
                        type = type,
                        identifier = identifier
                    ),
                    pos = pos
                )
            } else if (visibleEntity.type != type) {
                throw WrongTypeException(visibleEntity.type, actualType = type, pos = pos)
            }
        }
    } else { // a, b := 3, true. No tuple in semanticEntities.
        if (semanticEntities.size != identifierList.size) {
            throw WrongNumberOfExpressionsException(
                expected =  identifierList.size,
                actual = semanticEntities.size,
                pos = pos
            )
        }

        identifierList.zip(semanticEntities.map { it.type }).forEach { (identifier, type) ->
            val visibleEntity = scope.findVisibleObjectEntity(identifier)
            if (visibleEntity == null) {
                scope.addUniqueEntity(
                    entity = VarEntity(
                        type = type,
                        identifier = identifier
                    ),
                    pos = pos
                )
            } else if (visibleEntity.type != type) {
                throw WrongTypeException(visibleEntity.type, actualType = type, pos = pos)
            }
        }
    }

    return StatementEntity()
}
