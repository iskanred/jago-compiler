package inno.jago.semantic

import inno.jago.ast.global.SourceFileNode

class TypeChecker(
    val sourceFileNode: SourceFileNode
) {

    fun startTypeCheck() {
        sourceFileNode.typecheck()
    }

    private fun SourceFileNode.typecheck() {

    }
}
