package inno.jago.semantic.analyzer.statement

import inno.jago.ast.model.statement.ConditionalForStatementNode
import inno.jago.ast.model.statement.ForClauseStatementNode
import inno.jago.ast.model.statement.ForStatementNode
import inno.jago.semantic.WrongTypeException
import inno.jago.semantic.analyzer.expression.toSemanticEntity
import inno.jago.semantic.model.ScopeNode
import inno.jago.semantic.model.StatementEntity
import inno.jago.semantic.model.Type

fun ForStatementNode.toSemanticEntity(scope: ScopeNode) = StatementEntity().also {
    val forScope = scope.createNewForScope()
    when (this) {
        is ConditionalForStatementNode -> {
            val conditionSemanticEntity = condition.toSemanticEntity(forScope)
            if (conditionSemanticEntity.type != Type.BoolType) {
                throw WrongTypeException(Type.BoolType, actualType = conditionSemanticEntity.type, pos = pos)
            }
        }
        is ForClauseStatementNode -> {
            initStatementNode?.toSemanticEntity(forScope)

            condition?.let {
                val conditionSemanticEntity = it.toSemanticEntity(forScope)
                if (conditionSemanticEntity.type != Type.BoolType) {
                    throw WrongTypeException(Type.BoolType, actualType = conditionSemanticEntity.type, pos = pos)
                }
            }

            postStatementNode?.toSemanticEntity(forScope)
        }
    }
    block.toSemanticEntity(forScope)
}

