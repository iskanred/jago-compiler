package inno.jago.ast.converter.statement

import inno.jago.ast.model.statement.DeclarationStatementNode
import inno.jago.ast.converter.declaration.toDeclarationNodes
import inno.jago.ast.converter.common.toPos

fun GoParser.DeclarationContext.toDeclarationStatementNodes(): List<DeclarationStatementNode> =
    toDeclarationNodes().map {
        DeclarationStatementNode(pos = toPos(), declaration = it )
    }
