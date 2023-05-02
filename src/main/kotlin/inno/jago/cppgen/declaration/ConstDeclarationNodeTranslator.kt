package inno.jago.cppgen.declaration

import inno.jago.ast.model.decl.ConstDeclarationNode

fun ConstDeclarationNode.translateToCode(): String {
    return "const ${this.identifier} = ${this.expression};"
}
