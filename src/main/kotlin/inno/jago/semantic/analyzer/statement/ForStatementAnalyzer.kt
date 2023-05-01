package inno.jago.semantic.analyzer.statement

import inno.jago.ast.model.statement.ConditionalForStatementNode
import inno.jago.ast.model.statement.ForClauseStatementNode
import inno.jago.ast.model.statement.ForStatementNode
import inno.jago.semantic.WrongTypeException
import inno.jago.semantic.analyzer.expression.toSemanticEntity
import inno.jago.semantic.model.EntityType
import inno.jago.semantic.model.ScopeNode
import inno.jago.semantic.model.SemanticEntity
import inno.jago.semantic.model.Type

fun ForStatementNode.toSemanticEntity(scope: ScopeNode) = SemanticEntity(
    type = Type.UnitType,
    pos = pos,
    entityType = EntityType.NO_IDENTIFIER
).also {
    val forScope = scope.createNewForScope("'for' scope in ${scope.name}")
    when (this) {
        is ConditionalForStatementNode -> {
            val conditionSemanticEntity = condition.toSemanticEntity(scope)
            if (conditionSemanticEntity.type != Type.BoolType) {
                throw WrongTypeException(Type.BoolType, actual = conditionSemanticEntity)
            }
        }
        is ForClauseStatementNode -> {
            initStatementNode?.toSemanticEntity(forScope)

            condition?.let {
                val conditionSemanticEntity = it.toSemanticEntity(scope)
                if (conditionSemanticEntity.type != Type.BoolType) {
                    throw WrongTypeException(Type.BoolType, actual = conditionSemanticEntity)
                }
            }

            postStatementNode?.toSemanticEntity(forScope)
        }
    }
    block.eachStatementToSemanticEntity(forScope)
}

