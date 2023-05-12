package inno.jago.cppgen.statement

import inno.jago.ast.model.statement.ReturnStatementNode
import inno.jago.cppgen.expression.translateToCode
import java.lang.StringBuilder

fun ReturnStatementNode.translateToCode(): String =
    if (expressions.isEmpty()) {
        "return"
    } else {
        val returnInstruction = StringBuilder("return ")
        if (expressions.size == 1) {
            returnInstruction.append(expressions[0].translateToCode())
        } else {
            returnInstruction.append("make_tuple(")
            for (i in expressions.indices) {
                returnInstruction.append(expressions[i].translateToCode())
                if (i != expressions.size - 1) {
                    returnInstruction.append(", ")
                }
            }
            returnInstruction.append(")")
        }
        returnInstruction.toString()
    }
