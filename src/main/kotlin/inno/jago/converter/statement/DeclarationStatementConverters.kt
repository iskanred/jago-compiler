package inno.jago.converter.statement

import inno.jago.ast.statement.DeclarationStatementNode
import inno.jago.converter.toDeclarationNodes
import inno.jago.converter.toPos

fun GoParser.DeclarationContext.toDeclarationStatementNode(): DeclarationStatementNode{
    return DeclarationStatementNode(
        pos = toPos(),
        declaration = toDeclarationNodes().first()
    )
}
