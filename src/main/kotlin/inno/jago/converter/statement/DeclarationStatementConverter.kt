package inno.jago.converter.statement

import inno.jago.ast.statement.DeclarationStatementNode
import inno.jago.converter.declaration.toDeclarationNodes
import inno.jago.converter.common.toPos

fun GoParser.DeclarationContext.toDeclarationStatementNodes(): List<DeclarationStatementNode> =
    toDeclarationNodes().map {
        DeclarationStatementNode(pos = toPos(), declaration = it )
    }
