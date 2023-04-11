package inno.jago.converter

import inno.jago.EntityNotSupported
import inno.jago.UnreachableCodeException
import inno.jago.WrongNumberOfExpressions
import inno.jago.ast.decl.ConstDeclarationNode
import inno.jago.ast.decl.DeclarationNode
import inno.jago.ast.decl.FunctionDeclarationNode
import inno.jago.ast.decl.VarDeclarationNode
import inno.jago.converter.expression.toExpressionNode

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
    val identifiers = identifierList().IDENTIFIER().map { it.text }
    val expressions = expressionList().expression().map { it.toExpressionNode() }

    if (identifiers.size != expressions.size ) {
        throw WrongNumberOfExpressions(expected = identifiers.size, actual = expressions.size)
    }

    return identifiers.mapIndexed { index, identifier ->
        ConstDeclarationNode(
            pos = toPos(),
            identifier = identifier,
            type = type().toTypeNode(),
            expression = expressions[index]
        )
    }
}

fun GoParser.VarSpecContext.toVarDeclarationNodes(): List<VarDeclarationNode> {
    val identifiers = identifierList().IDENTIFIER().map { it.text }
    val expressions = expressionList().expression().map { it.toExpressionNode() }

    return if (expressions.size == 1) {
        identifiers.mapIndexed { index, identifier ->
            VarDeclarationNode(
                pos = toPos(),
                identifier = identifier,
                type = type().toTypeNode(),
                expression = expressions.first(),
                positionInRow = index
            )
        }
    } else if (identifiers.size == expressions.size) {
        identifiers.mapIndexed { index, identifier ->
            VarDeclarationNode(
                pos = toPos(),
                identifier = identifier,
                type = type().toTypeNode(),
                expression = expressions[index],
                positionInRow = index
            )
        }
    } else {
        throw WrongNumberOfExpressions(identifiers.size, expressions.size)
    }
}

fun GoParser.FunctionDeclContext.toFunctionDeclarationNode() = FunctionDeclarationNode(
    pos = toPos(),
    functionName = functionName().IDENTIFIER().text,
    signature = signature().toSignatureNode(),
    functionBody = functionBody().toBlockStatementNode()
)
