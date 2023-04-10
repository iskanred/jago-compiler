package inno.jago.converter

import inno.jago.EntityNotSupported
import inno.jago.UnreachableCodeException
import inno.jago.ast.decl.DeclarationNode
import inno.jago.ast.decl.FunctionDeclarationNode
import inno.jago.lexer.Pos

fun GoParser.DeclarationContext.toDeclarationNodes(): List<DeclarationNode> {
    constDecl()?.let {
        return TODO()
    }
    varDecl()?.let {
        return TODO()
    }
    typeDecl()?.let {
        throw EntityNotSupported("Types")
    }
    throw UnreachableCodeException()
}

fun GoParser.FunctionDeclContext.toFunctionDeclarationNode() = FunctionDeclarationNode(
    pos = Pos(tokenStart = start, tokenStop = stop),
    functionName = functionName().IDENTIFIER().text,
    signature = TODO(),
    functionBody = TODO()
)
