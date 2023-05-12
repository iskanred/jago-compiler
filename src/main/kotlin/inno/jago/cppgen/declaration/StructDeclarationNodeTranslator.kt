package inno.jago.cppgen.declaration

import inno.jago.ast.model.decl.StructDeclarationNode
import inno.jago.ast.model.type.StructTypeNode
import inno.jago.cppgen.type.translateToCode
import java.lang.RuntimeException

fun StructDeclarationNode.translateToCode(): String {
    var structText = "struct $identifier {\n";

    if (this.type !is StructTypeNode) {
        throw RuntimeException("а че а как")
    }
    for (field in this.type.fields) {
        structText = "$structText ${field.value.translateToCode()} ${field.key};\n"
    }

    return "$structText};\n";
}
