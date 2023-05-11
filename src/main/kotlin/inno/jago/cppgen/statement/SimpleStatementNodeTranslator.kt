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
import java.security.MessageDigest

fun SimpleStatementNode.translateToCode(): String = when (this) {
    is AssignmentNode -> translateToCode() + ";"
    is EmptyStatementNode -> ";"
    is ExpressionStatementNode -> expression.translateToCode() + ";"
    is IncDecStatementNode -> translateToCode() + ";"
    is ShortVarDeclNode -> translateToCode() + ";"
}

fun AssignmentNode.translateToCode(): String {
    val translatedLeftExpressions = leftExpressions.map { it.translateToCode() }
    val translatedRightExpressions = rightExpressions.map { it.translateToCode() }

    var res = ""
    if (leftExpressions.size != rightExpressions.size) {
        res = "tie(${translatedLeftExpressions.joinToString()}) = ${translatedRightExpressions.first()}"
    } else {
        translatedLeftExpressions.zip(translatedRightExpressions).forEach { (lhs, rhs) ->
            res += when (val it = assignOperator) {
                is SimpleAssignOperatorNode -> "$lhs = $rhs;"
                is AddOpSimpleAssignOperatorNode -> "$lhs ${it.addOperator.translateToCode()}= $rhs;"
                is MulOpSimpleAssignOperatorNode -> "$lhs ${it.mulOperator.translateToCode()}= $rhs;"
            }
        }
        res = res.dropLast(1)
    }

    return res
}

fun IncDecStatementNode.translateToCode(): String = when (type) {
    IncDecStatementNode.IncDec.INC -> "${expression.translateToCode()}++"
    IncDecStatementNode.IncDec.DEC -> "${expression.translateToCode()}--"
}

fun ShortVarDeclNode.translateToCode(): String {
    var res = ""
    if (identifierList.size != expression.size) {
        val uniqueName = generateUniqueTupleName(identifierList.joinToString(""))
        res = "auto $uniqueName = ${expression.first().translateToCode()};\n"

        for (i in 0 until this.identifierList.size) {
            if (identifierList[i] == "_") {
                continue
            }

            res += "auto ${identifierList[i]} = get<$i>($uniqueName);"
        }
    } else {
        identifierList.zip(expression.map { it.translateToCode() }).forEach { (identifier, expressionCode) ->
            if (identifier != "_") {
                res += "auto $identifier = $expressionCode;"
            }
        }
    }

    return res.dropLast(1)
}

private fun generateUniqueTupleName(base: String): String {
    val bytes = base.toByteArray(Charsets.UTF_8)
    val digest = MessageDigest.getInstance("SHA-256").digest(bytes)
    return "tuple_" + digest.fold("") { str, it -> str + "%02x".format(it) }
}

