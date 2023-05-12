package inno.jago.cppgen.statement

import inno.jago.ast.model.statement.IfStatementNode
import inno.jago.cppgen.expression.translateToCode

fun IfStatementNode.translateToCode(): String {
    val simpleStatementCode = StringBuilder("{\n")

    if (simpleStatement != null) {
        simpleStatementCode.append(simpleStatement!!.translateToCode())
        simpleStatementCode.append(";\n")
    }

    simpleStatementCode.append(
        "if (${expression.translateToCode()}) ${block.translateToCode()} ${elseBranch?.translateToCode() ?: ""} }\n"
    )

    return simpleStatementCode.toString()
}
