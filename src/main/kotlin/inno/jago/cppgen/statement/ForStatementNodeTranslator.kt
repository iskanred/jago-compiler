package inno.jago.cppgen.statement

import inno.jago.ast.model.statement.ConditionalForStatementNode
import inno.jago.ast.model.statement.EmptyStatementNode
import inno.jago.ast.model.statement.ForClauseStatementNode
import inno.jago.ast.model.statement.ForStatementNode
import inno.jago.cppgen.expression.translateToCode

fun ForStatementNode.translateToCode(): String = when (this) {
    is ConditionalForStatementNode -> translateToCode()
    is ForClauseStatementNode -> translateToCode()
}

fun ConditionalForStatementNode.translateToCode(): String {
    return "while (${condition.translateToCode()}) ${block.translateToCode()}"
}

fun ForClauseStatementNode.translateToCode(): String {
    if (initStatementNode == null && condition == null && postStatementNode == null) {
        return "while (true) ${block.translateToCode()}"
    }

    return "for (${initStatementNode?.translateToCode() ?: ""}" +"${condition?.translateToCode() ?: ""}; ${postStatementNode?.translateToCode()?.dropLast(1) ?: ""}) ${block.translateToCode()}"
}
