package inno.jago.cppgen.declaration

import inno.jago.ast.model.decl.VarDeclarationNode

fun VarDeclarationNode.translateToCode(): String {
    return "auto ${this.identifier} = ${this.expression};"
}
