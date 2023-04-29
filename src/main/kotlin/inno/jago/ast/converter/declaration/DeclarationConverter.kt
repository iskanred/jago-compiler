package inno.jago.ast.converter.declaration

import inno.jago.ast.EntityNotSupportedException
import inno.jago.ast.WrongNumberOfExpressionsException
import inno.jago.exception.UnreachableCodeException
import inno.jago.ast.model.decl.ConstDeclarationNode
import inno.jago.ast.model.decl.DeclarationNode
import inno.jago.ast.model.decl.FunctionDeclarationNode
import inno.jago.ast.model.decl.VarDeclarationNode
import inno.jago.ast.converter.common.toPos
import inno.jago.ast.converter.expression.toExpressionNode
import inno.jago.ast.converter.statement.toBlockStatementNode
import inno.jago.ast.converter.signature.toSignatureNode
import inno.jago.ast.converter.type.toTypeNode

fun GoParser.DeclarationContext.toDeclarationNodes(): List<DeclarationNode> {
    constDecl()?.let { constDecl ->
        return constDecl.constSpec().flatMap { constSpec ->
            constSpec.toConstDeclarationNode()
        }
    }
    varDecl()?.let { varDecl ->
        return varDecl.varSpec().flatMap { varSpec ->
            varSpec.toVarDeclarationNodes()
        }
    }
    typeDecl()?.let {
        throw EntityNotSupportedException("Types")
    }
    throw UnreachableCodeException()
}

fun GoParser.ConstSpecContext.toConstDeclarationNode(): List<ConstDeclarationNode> {
    val identifiers = identifierList().IDENTIFIER().map { it.text }
    val expressions = expressionList().expression().map { it.toExpressionNode() }

    if (identifiers.size != expressions.size ) {
        throw WrongNumberOfExpressionsException(expected = identifiers.size, actual = expressions.size)
    }

    return identifiers.mapIndexed { index, identifier ->
        ConstDeclarationNode(
            pos = toPos(),
            identifier = identifier,
            type = type()?.toTypeNode(),
            expression = expressions[index]
        )
    }
}

fun GoParser.VarSpecContext.toVarDeclarationNodes(): List<VarDeclarationNode> {
    val identifiers = identifierList().IDENTIFIER().map { it.text }
    val expressions = expressionList()?.expression()?.map { it.toExpressionNode() }

    return if (expressions == null || expressions.size == 1) {
        identifiers.mapIndexed { index, identifier ->
            VarDeclarationNode(
                pos = toPos(),
                identifier = identifier,
                type = type()?.toTypeNode(),
                expression = expressions?.first(),
                positionInRow = index
            )
        }
    } else if (identifiers.size == expressions.size) {
        identifiers.mapIndexed { index, identifier ->
            VarDeclarationNode(
                pos = toPos(),
                identifier = identifier,
                type = type()?.toTypeNode(),
                expression = expressions[index],
                positionInRow = index
            )
        }
    } else {
        throw WrongNumberOfExpressionsException(identifiers.size, expressions.size)
    }
}

fun GoParser.FunctionDeclContext.toFunctionDeclarationNode() = FunctionDeclarationNode(
    pos = toPos(),
    functionName = functionName().IDENTIFIER().text,
    signature = signature().toSignatureNode(),
    functionBody = functionBody().toBlockStatementNode()
)
