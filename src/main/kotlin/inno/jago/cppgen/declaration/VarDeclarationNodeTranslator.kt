package inno.jago.cppgen.declaration

import inno.jago.ast.model.decl.VarDeclarationNode
import inno.jago.cppgen.expression.translateToCode
import inno.jago.cppgen.type.translateToCode
import inno.jago.semantic.SemanticException

// var a, b, c = someFunc()
fun VarDeclarationNode.translateToCode(): String {
    if (expression != null) {
        return "auto ${this.identifier} = ${this.expression.translateToCode()}"
    }
    if (type != null) {
        return "${this.type.translateToCode()} ${this.identifier}"
    }
    throw SemanticException("Var decl must contain either type either expression")
}
