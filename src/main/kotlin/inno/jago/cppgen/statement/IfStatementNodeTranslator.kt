package inno.jago.cppgen.statement

import inno.jago.ast.model.statement.IfStatementNode
import inno.jago.cppgen.expression.translateToCode

fun IfStatementNode.translateToCode(): String {
    // TODO: Возможно, стоит менять название переменной на UUID
    //  для уникальности переменных в области видимости
    var simpleStatementCode = ""
    if (simpleStatement != null) {
        simpleStatementCode = simpleStatement!!.translateToCode() + "; "
    }

    return simpleStatementCode + "if (${expression.translateToCode()}) ${block.translateToCode()} ${elseBranch?.translateToCode() ?: ""}"
}

