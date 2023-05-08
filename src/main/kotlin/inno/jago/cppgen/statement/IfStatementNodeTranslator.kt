package inno.jago.cppgen.statement

import inno.jago.ast.model.statement.IfStatementNode
import inno.jago.ast.model.statement.ShortVarDeclNode
import inno.jago.cppgen.expression.translateToCode
import org.intellij.lang.annotations.Identifier
import java.util.*

fun IfStatementNode.translateToCode(): String {
    // TODO: Возможно, стоит менять название переменной на UUID
    //  для уникальности переменных в области видимости
    var simpleStatementCode = ""

    if (simpleStatement != null) {
        when (simpleStatement){
            // rename variables for inner scope
            is ShortVarDeclNode  -> {
                var identifiers = mutableMapOf<String, String>()
                var oldIdentifiers = (simpleStatement as ShortVarDeclNode).identifierList.toMutableList()

                for (i in oldIdentifiers.indices){
                    var newIdentifier = generateNewIdentifier()
                    identifiers[oldIdentifiers[i]] = newIdentifier
                    oldIdentifiers[i] = newIdentifier
                }
                var newDeclNode = ShortVarDeclNode((simpleStatement as ShortVarDeclNode).pos, oldIdentifiers, (simpleStatement as ShortVarDeclNode).expression)
                simpleStatementCode = newDeclNode.translateToCode() + ";\n"


                // rename variables inside scope
                simpleStatementCode += "if (${expression.translateToCode()}) ${block.translateToCode()} ${elseBranch?.translateToCode() ?: ""}"
                for ((key, value) in identifiers) {
                    simpleStatementCode = smartReplace(simpleStatementCode, key, value)
                }

                return simpleStatementCode

            }
            else -> {
                simpleStatementCode = simpleStatement!!.translateToCode() + ";\n"
            }
        }
    }

    return simpleStatementCode + "if (${expression.translateToCode()}) ${block.translateToCode()} ${elseBranch?.translateToCode() ?: ""}"
}

fun generateNewIdentifier(): String{
    var newIdentifier = "idnt_" + UUID.randomUUID().toString()
    newIdentifier = newIdentifier.replace("-", "_")
    return newIdentifier
}

fun smartReplace(source: String, oldValue: String, newValue: String): String{
    // <space>oldVal<space>
    var newCode  = source.replace(" $oldValue ", " $newValue ")
    // ... (oldVar) ...
    newCode = newCode.replace("($oldValue)", "($newValue)")
    // ... (oldVar, ...
    newCode = newCode.replace("($oldValue,", "($newValue,")
    // oldVar)
    newCode = newCode.replace("$oldValue)", "$newValue)")
    // oldVar,
    newCode = newCode.replace("$oldValue,", "$newValue,")
    // oldVar++
    newCode = newCode.replace("$oldValue++", "$newValue++")
    // oldVar--
    newCode = newCode.replace("$oldValue--", "$newValue--")


    return newCode
}
