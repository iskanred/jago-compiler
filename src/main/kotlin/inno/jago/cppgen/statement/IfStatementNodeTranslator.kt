package inno.jago.cppgen.statement

import inno.jago.ast.model.statement.IfStatementNode
import inno.jago.cppgen.expression.translateToCode

fun IfStatementNode.translateToCode(): String {
    var simpleStatementCode = "{\n"

    if (simpleStatement != null) {
        simpleStatementCode += simpleStatement!!.translateToCode() + ";\n"
    }

    return simpleStatementCode + "if (${expression.translateToCode()}) ${block.translateToCode()} ${elseBranch?.translateToCode() ?: ""} }\n"
}
