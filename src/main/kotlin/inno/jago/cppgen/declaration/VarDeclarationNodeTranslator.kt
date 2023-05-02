package inno.jago.cppgen.declaration

import inno.jago.ast.model.decl.VarDeclarationNode

// var a, b, c = someFunc()
fun VarDeclarationNode.translateToCode(): String {
    TODO()
    return "auto ${this.identifier} = ${this.expression};"
}
