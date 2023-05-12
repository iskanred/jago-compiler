package inno.jago.cppgen.statement

import inno.jago.ast.model.statement.IfStatementNode
import inno.jago.cppgen.expression.translateToCode

fun IfStatementNode.translateToCode(): String {
    var simpleStatementCode = ""
    if(this.simpleStatement!=null) {
        simpleStatementCode = "{\n"
    }
    if (simpleStatement != null) {
        simpleStatementCode += simpleStatement!!.translateToCode() + ";\n"
    }
    simpleStatementCode += "if (${expression.translateToCode()}) ${block.translateToCode()} ${elseBranch?.translateToCode() ?: ""}"

    if(this.simpleStatement!=null) {
        simpleStatementCode += "}\n"
    }
    return simpleStatementCode
}
