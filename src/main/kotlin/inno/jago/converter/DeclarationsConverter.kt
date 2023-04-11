package inno.jago.converter

import inno.jago.EntityNotSupported
import inno.jago.UnreachableCodeException
import inno.jago.ast.decl.ConstDeclarationNode
import inno.jago.ast.decl.DeclarationNode
import inno.jago.ast.decl.FunctionDeclarationNode
import inno.jago.ast.decl.VarDeclarationNode
import inno.jago.ast.type.TypeNode
import inno.jago.lexer.Pos
import org.antlr.v4.runtime.ParserRuleContext

fun GoParser.DeclarationContext.toDeclarationNodes(): List<DeclarationNode> {
    constDecl()?.let { constDecl ->
        return constDecl.constSpec().flatMap { constSpec ->
            constSpec.toConstDeclarationNode()
        }
    }

    varDecl()?.let {
        return it.varSpec().flatMap { varSpec ->
            varSpec.toVarDeclarationNodes()
        }
    }

    typeDecl()?.let {
        throw EntityNotSupported("Types")
    }

    throw UnreachableCodeException()
}

fun GoParser.ConstSpecContext.toConstDeclarationNode(): List<ConstDeclarationNode> {

}

fun GoParser.VarSpecContext.toVarDeclarationNodes(): List<VarDeclarationNode> {
    var identifiers = identifierList().IDENTIFIER().map { it.text }
    var type = type().toTypeNode()
    var expressions = expressionList().expression().map { it.toExpressionNode() }


}

fun GoParser.FunctionDeclContext.toFunctionDeclarationNode(): FunctionDeclarationNode {
    return FunctionDeclarationNode(
        pos = toPos(),
        functionName = functionName().IDENTIFIER().text,
        signature = TODO(),
        functionBody = functionBody().toBlockStatementNode()
    )
}
