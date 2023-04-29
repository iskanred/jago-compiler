package inno.jago.semantic.analysis.statement

import inno.jago.ast.model.statement.AssignmentNode
import inno.jago.ast.model.statement.ExpressionStatementNode
import inno.jago.ast.model.statement.IfStatementNode
import inno.jago.ast.model.statement.IncDecStatementNode
import inno.jago.ast.model.statement.StatementNode
import inno.jago.semantic.WrongTypeException
import inno.jago.semantic.analysis.expression.toSemanticEntity
import inno.jago.semantic.model.ScopeNode
import inno.jago.semantic.model.SemanticEntity
import inno.jago.semantic.model.Type

fun StatementNode.toSemanticEntity(scope: ScopeNode): SemanticEntity {
    when (this) {
        is AssignmentNode -> {
            TODO()
        }
        is IfStatementNode -> {
            TODO()
        }
        is ExpressionStatementNode -> {
            TODO()
        }
        is IncDecStatementNode -> {
            val semanticEntity = expression.toSemanticEntity(scope)
            if (semanticEntity.type != Type.IntegerType || semanticEntity.type != Type.DoubleType) {
                throw WrongTypeException(Type.IntegerType, semanticEntity)
            }
            return semanticEntity
        }
        else -> throw NotImplementedError()
    }
}
