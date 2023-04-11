package inno.jago.converter

import inno.jago.EntityNotSupported
import inno.jago.UnreachableCodeException
import inno.jago.WrongNumberOfExpressions
import inno.jago.ast.decl.ConstDeclarationNode
import inno.jago.ast.decl.DeclarationNode
import inno.jago.ast.decl.FunctionDeclarationNode
import inno.jago.ast.decl.VarDeclarationNode

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
    var identifiers = identifierList().IDENTIFIER().map { it.text }
    var expressions = expressionList().expression().map { it.toExpressionNode() }

    if (identifiers.size != expressions.size ) {
        throw WrongNumberOfExpressions(identifiers.size, expressions.size)
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
    var identifiers = identifierList().IDENTIFIER().map { it.text }
    var expressions = expressionList().expression().map { it.toExpressionNode() }

    if (identifiers.size != expressions.size || expressions.size != 1) {
        throw WrongNumberOfExpressions(identifiers.size, expressions.size)
    }

    if (expressions.size == 1) {
        return identifiers.mapIndexed { index, identifier ->
            VarDeclarationNode(
                pos = toPos(),
                identifier = identifier,
                type = type().toTypeNode(),
                expression = expressions.first(),
                positionInRow = index
            )
        }
    } else {
        return identifiers.mapIndexed { index, identifier ->
            VarDeclarationNode(
                pos = toPos(),
                identifier = identifier,
                type = type().toTypeNode(),
                expression = expressions[index],
                positionInRow = index
            )
        }
    }
}

fun GoParser.FunctionDeclContext.toFunctionDeclarationNode(): FunctionDeclarationNode {
    return FunctionDeclarationNode(
        pos = toPos(),
        functionName = functionName().IDENTIFIER().text,
        signature = TODO(),
        functionBody = functionBody().toBlockStatementNode()
    )
}
