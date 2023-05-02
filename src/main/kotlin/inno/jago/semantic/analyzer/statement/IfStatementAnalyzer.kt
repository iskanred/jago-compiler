package inno.jago.semantic.analyzer.statement

import inno.jago.ast.model.statement.ElseIfStatementNode
import inno.jago.ast.model.statement.ElseStatementNode
import inno.jago.ast.model.statement.IfStatementNode
import inno.jago.ast.model.statement.SimpleElseStatementNode
import inno.jago.semantic.WrongTypeException
import inno.jago.semantic.analyzer.expression.toSemanticEntity
import inno.jago.semantic.model.ScopeNode
import inno.jago.semantic.model.StatementEntity
import inno.jago.semantic.model.Type

fun IfStatementNode.toSemanticEntity(scope: ScopeNode): StatementEntity {
    val ifScope = scope.createNewIfScope()
    simpleStatement?.toSemanticEntity(ifScope)

    val actualConditionSemanticEntity = expression.toSemanticEntity(ifScope)
    if (actualConditionSemanticEntity.type != Type.BoolType) {
        throw WrongTypeException(Type.BoolType, actualType = actualConditionSemanticEntity.type, pos = pos)
    }

    block.toSemanticEntity(ifScope)
    elseBranch?.toSemanticEntity(scope)

    return StatementEntity()
}

fun ElseStatementNode.toSemanticEntity(scope: ScopeNode) = when(this) {
    is ElseIfStatementNode -> ifStmt.toSemanticEntity(scope)
    is SimpleElseStatementNode -> block.toSemanticEntity(scope)
}
