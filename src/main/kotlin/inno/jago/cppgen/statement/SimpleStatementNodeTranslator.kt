package inno.jago.cppgen.statement

import inno.jago.ast.model.statement.AddOpSimpleAssignOperatorNode
import inno.jago.ast.model.statement.AssignmentNode
import inno.jago.ast.model.statement.EmptyStatementNode
import inno.jago.ast.model.statement.ExpressionStatementNode
import inno.jago.ast.model.statement.IncDecStatementNode
import inno.jago.ast.model.statement.MulOpSimpleAssignOperatorNode
import inno.jago.ast.model.statement.ShortVarDeclNode
import inno.jago.ast.model.statement.SimpleAssignOperatorNode
import inno.jago.ast.model.statement.SimpleStatementNode
import inno.jago.cppgen.expression.binary_expression.translateToCode
import inno.jago.cppgen.expression.translateToCode
fun SimpleStatementNode.translateToCode(): String = when (this) {
    is AssignmentNode -> translateToCode() + ";"
    is EmptyStatementNode -> ";"
    is ExpressionStatementNode -> expression.translateToCode() + ";"
    is IncDecStatementNode -> translateToCode() + ";"
    is ShortVarDeclNode -> translateToCode() + ";"
}

fun AssignmentNode.translateToCode(): String {
    if (leftExpressions.size != rightExpressions.size) {
        TODO("Сделать для кортежей")
    }

    val translatedLeftExpressions = leftExpressions.map { it.translateToCode() }
    val translatedRightExpressions = rightExpressions.map { it.translateToCode() }

    var res = ""
    translatedLeftExpressions.zip(translatedRightExpressions).forEach { (lhs, rhs) ->
        res += when (val it = assignOperator) {
            is SimpleAssignOperatorNode -> "$lhs = $rhs"
            is AddOpSimpleAssignOperatorNode -> "$lhs ${it.addOperator.translateToCode()}= $rhs"
            is MulOpSimpleAssignOperatorNode -> "$lhs ${it.mulOperator.translateToCode()}= $rhs"
        }
    }

    return res
}

fun IncDecStatementNode.translateToCode(): String = when (type) {
    IncDecStatementNode.IncDec.INC -> "${expression.translateToCode()}++"
    IncDecStatementNode.IncDec.DEC -> "${expression.translateToCode()}--"
}

fun ShortVarDeclNode.translateToCode(): String {
    if (identifierList.size != expression.size) {
        TODO("Сделать для кортежей")
    }

    var res = ""
    identifierList.zip(expression.map { it.translateToCode() }).forEach { (identifier, expressionCode) ->
        if (identifier != "_") {
            res += "auto $identifier = $expressionCode"
        }
    }

    return res
}

