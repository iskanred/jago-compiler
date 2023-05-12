package inno.jago.cppgen.declaration

import inno.jago.ast.model.decl.VarDeclarationNode
import inno.jago.ast.model.expression.unary_expression.ApplicationExpressionNode
import inno.jago.cppgen.expression.translateToCode
import inno.jago.cppgen.type.translateToCode
import inno.jago.semantic.SemanticException
import inno.jago.semantic.model.Type
import inno.jago.semantic.model.toType
import java.lang.StringBuilder

// var a, b, c = someFunc()
@Suppress("ReturnCount")
fun VarDeclarationNode.translateToCode(): String = if (this.identifier == "_") {
    ""
} else if (expression != null) {
    if (expression is ApplicationExpressionNode && this.positionInRow >= 0) {
        "auto ${this.identifier} = get<${this.positionInRow}>(${this.expression.translateToCode()})"
    } else {
        "auto ${this.identifier} = ${this.expression.translateToCode()}"
    }
} else if (type != null) {
    val additionalInfo = type.toType().let {
        if (it is Type.ArrayType) {
            "(${it.length})"
        } else {
            ""
        }
    }

    "${this.type.translateToCode()} ${this.identifier}$additionalInfo"
} else {
    throw SemanticException("Var decl must contain either type or expression")
}
