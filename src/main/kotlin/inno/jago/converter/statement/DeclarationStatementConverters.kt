package inno.jago.converter.statement

import inno.jago.ast.statement.BlockStatementNode
import inno.jago.ast.statement.DeclarationStatementNode
import inno.jago.converter.toDeclarationNodes
import inno.jago.converter.toPos
import inno.jago.lexer.Pos
fun GoParser.DeclarationContext.toDeclarationStatementNode(): DeclarationStatementNode{
    return DeclarationStatementNode(
        pos = toPos(),
        declaration = toDeclarationNodes().first()
    )
}
