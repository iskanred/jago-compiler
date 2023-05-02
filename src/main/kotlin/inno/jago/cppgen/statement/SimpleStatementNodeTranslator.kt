package inno.jago.cppgen.statement

import inno.jago.ast.model.statement.AssignmentNode
import inno.jago.ast.model.statement.EmptyStatementNode
import inno.jago.ast.model.statement.ExpressionStatementNode
import inno.jago.ast.model.statement.IncDecStatementNode
import inno.jago.ast.model.statement.ShortVarDeclNode
import inno.jago.ast.model.statement.SimpleStatementNode
import inno.jago.cppgen.expression.translateToCode

fun SimpleStatementNode.translateToCode(): String = when (this) {
    is AssignmentNode -> translateToCode()
    is EmptyStatementNode -> ";"
    is ExpressionStatementNode -> expression.translateToCode()
    is IncDecStatementNode -> translateToCode()
    is ShortVarDeclNode -> translateToCode()
}

fun AssignmentNode.translateToCode(): String {

}

fun IncDecStatementNode.translateToCode(): String = when (type) {
    IncDecStatementNode.IncDec.INC -> "${expression.translateToCode()}++"
    IncDecStatementNode.IncDec.DEC -> "${expression.translateToCode()}--"
}

fun ShortVarDeclNode.translateToCode(): String {

}

