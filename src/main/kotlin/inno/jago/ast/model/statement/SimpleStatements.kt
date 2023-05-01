package inno.jago.ast.model.statement

import inno.jago.ast.model.expression.ExpressionNode
import inno.jago.ast.model.expression.binary_expression.AddOperator
import inno.jago.ast.model.expression.binary_expression.MulOperator
import inno.jago.lexer.Pos

sealed class SimpleStatementNode (pos: Pos) : StatementNode(pos = pos)

class IncDecStatementNode (
    pos: Pos,
    val expression: ExpressionNode,
    val type: IncDec
) : SimpleStatementNode(pos = pos) {

    enum class IncDec {
        INC,
        DEC
    }
}

class AssignmentNode (
    pos: Pos,
    val leftExpressions: List<ExpressionNode>,
    val rightExpressions: List<ExpressionNode>,
    val assignOperator: AssignOperatorNode
) : SimpleStatementNode(pos = pos)


sealed class AssignOperatorNode

object SimpleAssignOperatorNode : AssignOperatorNode()
class AddOpSimpleAssignOperatorNode(val addOperator: AddOperator) : AssignOperatorNode()

class MulOpSimpleAssignOperatorNode(val mulOperator: MulOperator) : AssignOperatorNode()

class ShortVarDeclNode (
    pos: Pos,
    val identifierList: List<String>,
    val expression: List<ExpressionNode>,
) : SimpleStatementNode(pos = pos)

class ExpressionStatementNode (
    pos: Pos,
    val expression: ExpressionNode
) : SimpleStatementNode(pos = pos)

class EmptyStatementNode(pos : Pos) : SimpleStatementNode(pos = pos)
