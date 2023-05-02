package inno.jago.cppgen.declaration

import inno.jago.ast.model.decl.ConstDeclarationNode
import inno.jago.ast.model.decl.DeclarationNode
import inno.jago.ast.model.decl.VarDeclarationNode

fun DeclarationNode.translateToCode(): String = when(this) {
    is VarDeclarationNode -> {
        translateToCode()
    }

    is ConstDeclarationNode -> {
        translateToCode()
    }
}
