package inno.jago.ast.converter.declaration

import inno.jago.common.UnreachableCodeException
import inno.jago.ast.model.decl.ConstDeclarationNode
import inno.jago.ast.model.decl.DeclarationNode
import inno.jago.ast.model.decl.FunctionDeclarationNode
import inno.jago.ast.model.decl.VarDeclarationNode
import inno.jago.ast.converter.common.toPos
import inno.jago.ast.converter.expression.toExpressionNode
import inno.jago.ast.converter.statement.toBlockStatementNode
import inno.jago.ast.converter.signature.toSignatureNode
import inno.jago.ast.converter.type.toTypeNode
import inno.jago.ast.model.decl.StructDeclarationNode
import inno.jago.common.WrongNumberOfExpressionsException

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
    typeDecl()?.let { typeDecl ->
        val typeDef = typeDecl.typeSpec()[0].typeDef()
        val structName = typeDef.IDENTIFIER().text
        val fields = typeDef.type().toTypeNode()

        return listOf(StructDeclarationNode(
            pos = toPos(),
            identifier = structName,
            type = fields
        ))
    }
    throw UnreachableCodeException()
}

fun GoParser.ConstSpecContext.toConstDeclarationNode(): List<ConstDeclarationNode> {
    val identifiers = identifierList().IDENTIFIER().map { it.text }
    val expressions = expressionList().expression().map { it.toExpressionNode() }

    if (identifiers.size != expressions.size ) {
        throw WrongNumberOfExpressionsException(
            expected = identifiers.size,
            actual = expressions.size,
            pos = toPos()
        )
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

    return if (identifiers.size == 1 && (expressions == null || expressions.size == 1)) { // var a = func()
        identifiers.mapIndexed { index, identifier ->
            VarDeclarationNode(
                pos = toPos(),
                identifier = identifier,
                type = type()?.toTypeNode(),
                expression = expressions?.first(),
                positionInRow = -1,
                numberOfDeclarationsInRow = identifiers.size
            )
        }
    } else if (expressions == null || expressions.size == 1) { // var a, b = func()
        identifiers.mapIndexed { index, identifier ->
            VarDeclarationNode(
                pos = toPos(),
                identifier = identifier,
                type = type()?.toTypeNode(),
                expression = expressions?.first(),
                positionInRow = index,
                numberOfDeclarationsInRow = identifiers.size
            )
        }
    } else if (identifiers.size == expressions.size) { // var a, b = 1, true
        identifiers.mapIndexed { index, identifier ->
            VarDeclarationNode(
                pos = toPos(),
                identifier = identifier,
                type = type()?.toTypeNode(),
                expression = expressions[index],
                positionInRow = -1,
                numberOfDeclarationsInRow = identifiers.size
            )
        }
    } else {
        throw WrongNumberOfExpressionsException(
            expected = identifiers.size,
            actual = expressions.size,
            pos = toPos()
        )
    }
}

fun GoParser.FunctionDeclContext.toFunctionDeclarationNode() = FunctionDeclarationNode(
    pos = toPos(),
    functionName = functionName().IDENTIFIER().text,
    signature = signature().toSignatureNode(),
    functionBody = functionBody().toBlockStatementNode()
)
