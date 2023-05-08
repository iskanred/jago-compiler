package inno.jago.cppgen.declaration

import inno.jago.ast.model.decl.VarDeclarationNode
import inno.jago.ast.model.expression.unary_expression.ApplicationExpressionNode
import inno.jago.cppgen.expression.translateToCode
import inno.jago.cppgen.type.translateToCode
import inno.jago.semantic.SemanticException

// var a, b, c = someFunc()
fun VarDeclarationNode.translateToCode(): String {
    if (this.identifier == "_") {
        return ""
    }

    if (expression != null) {
        if (expression is ApplicationExpressionNode && this.positionInRow >= 0) {
            return "auto ${this.identifier} = get<${this.positionInRow}>(${this.expression.translateToCode()})"
        }

        return "auto ${this.identifier} = ${this.expression.translateToCode()}"
    }
    if (type != null) {
        return "${this.type.translateToCode()} ${this.identifier}"
    }
    throw SemanticException("Var decl must contain either type either expression")
}
