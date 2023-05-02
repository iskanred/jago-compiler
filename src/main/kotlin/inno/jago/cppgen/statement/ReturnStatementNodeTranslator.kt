package inno.jago.cppgen.statement

import inno.jago.ast.model.statement.ReturnStatementNode
import inno.jago.cppgen.expression.translateToCode

fun ReturnStatementNode.translateToCode(): String {
    if (expressions.isEmpty()) {
        return "return;"
    }
    var returnInstruction = "return "

    if (expressions.size == 1) {
        returnInstruction += expressions[0].translateToCode() + ";"
    } else {
        returnInstruction += "make_tuple("
        for (i in 0 until expressions.size) {
            returnInstruction += expressions[i].translateToCode()
            if (i != expressions.size - 1) {
                returnInstruction += ", "
            }
        }
        returnInstruction += ");"
    }
    return returnInstruction
}
