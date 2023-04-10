package inno.jago.ast.statement

import inno.jago.ast.expression.ExpressionNode
import inno.jago.lexer.Pos

class IfStatements(
    pos: Pos,
    var simpleStatement: SimpleStatementNode,
    var expression: ExpressionNode,
    var block: BlockStatementNode,
    var elseBranch: ElseStatementNode
) : StatementNode(pos = pos)
