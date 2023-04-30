package inno.jago.semantic.analyzer.statement

import inno.jago.ast.model.statement.ElseIfStatementNode
import inno.jago.ast.model.statement.IfStatementNode
import inno.jago.ast.model.statement.SimpleElseStatementNode
import inno.jago.semantic.WrongTypeException
import inno.jago.semantic.analyzer.expression.toSemanticEntity
import inno.jago.semantic.model.EntityType
import inno.jago.semantic.model.ScopeNode
import inno.jago.semantic.model.SemanticEntity
import inno.jago.semantic.model.Type

fun IfStatementNode.toSemanticEntity(scope: ScopeNode): SemanticEntity {
    val ifScope = scope.createNewIfScope("if scope in ${scope.name}")
    simpleStatement?.toSemanticEntity(ifScope)

    val actualConditionSemanticEntity = expression.toSemanticEntity(ifScope)
    if (actualConditionSemanticEntity.type != Type.BoolType) {
        throw WrongTypeException(Type.BoolType, actualConditionSemanticEntity)
    }

    block.block.forEach { statementNode ->
        statementNode.toSemanticEntity(ifScope)
    }

    elseBranch?.let {
        when (it) {
            is ElseIfStatementNode -> it.ifStmt.toSemanticEntity(scope)
            is SimpleElseStatementNode -> it.block.block.forEach { statementNode ->
                statementNode.toSemanticEntity(ifScope)
            }
        }
    }

    return SemanticEntity(
        type = Type.UnitType,
        pos = pos,
        entityType = EntityType.STATEMENT
    )
}
