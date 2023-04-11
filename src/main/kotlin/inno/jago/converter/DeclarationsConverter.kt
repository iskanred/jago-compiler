package inno.jago.converter

import inno.jago.EntityNotSupported
import inno.jago.UnreachableCodeException
import inno.jago.ast.decl.DeclarationNode
import inno.jago.ast.decl.FunctionDeclarationNode
import inno.jago.ast.decl.VarDeclarationNode
import inno.jago.ast.type.TypeNode
import inno.jago.lexer.Pos
import org.antlr.v4.runtime.ParserRuleContext

fun GoParser.DeclarationContext.toDeclarationNodes(): List<DeclarationNode> {
    constDecl()?.let {
        return TODO()
    }

    varDecl()?.let {
        it.varSpec().forEach {
            it.toVarDeclarationNodes()
        }
    }

    typeDecl()?.let {
        throw EntityNotSupported("Types")
    }
    throw UnreachableCodeException()
}

fun GoParser.VarSpecContext.toVarDeclarationNodes(): List<VarDeclarationNode> {
    var ids = identifierList().IDENTIFIER()
    var exps = expressionList().expression()

}

fun GoParser.TypeContext.toTypeNode(): TypeNode {
    return TypeNode(
        pos = toPos(),
        name = typeName().IDENTIFIER().text
    )
}

fun GoParser.FunctionDeclContext.toFunctionDeclarationNode(): FunctionDeclarationNode {
    return FunctionDeclarationNode(
        pos = toPos(),
        functionName = functionName().IDENTIFIER().text,
        signature = TODO(),
        functionBody = functionBody().toBlockStatementNode()
    )
}

fun ParserRuleContext.toPos(): Pos {
    return Pos(tokenStart = start, tokenStop = stop)
}
