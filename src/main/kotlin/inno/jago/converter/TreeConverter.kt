package inno.jago.converter

import GoParser
import inno.jago.ConversionException
import inno.jago.EntityNotSupported
import inno.jago.UnreachableCodeException
import inno.jago.ast.ImportNode
import inno.jago.ast.PackageNode
import inno.jago.ast.SourceFileNode
import inno.jago.ast.decl.DeclarationNode
import inno.jago.ast.decl.FunctionDeclarationNode
import inno.jago.ast.decl.TopLevelDeclNode
import inno.jago.lexer.Pos
import javax.naming.OperationNotSupportedException

fun GoParser.SourceFileContext.toSourceFile(): SourceFileNode {
    return SourceFileNode(
        pos = Pos(tokenStart = start, tokenStop = stop),
        packageName = packageClause().toPackageNode(),
        importNodes = importDecl().toImportDeclNodes(),
        topLevelDecls = topLevelDecl().toTopLevelDeclarations()
    )
}

private fun GoParser.PackageClauseContext.toPackageNode(): PackageNode = PackageNode(
    pos = Pos(tokenStart = start, tokenStop = stop),
    name = packageName().text
)

private fun List<GoParser.ImportDeclContext>.toImportDeclNodes(): List<ImportNode> = flatMap { importDeclContext ->
    importDeclContext.importSpec().map {
        ImportNode(
            pos = Pos(tokenStart = it.start, tokenStop = it.stop),
            alias = it.packageName().text,
            path = it.importPath().text,
            importAll = (it.DOT() != null)
        )
    }
}

private fun List<GoParser.TopLevelDeclContext>.toTopLevelDeclarations(): List<TopLevelDeclNode> = flatMap {
    it.toTopLevelDeclaration()
}

private fun GoParser.TopLevelDeclContext.toTopLevelDeclaration(): List<TopLevelDeclNode> {
    declaration()?.let {
        return it.toDeclarationNodes()
    }
    functionDecl()?.let {
        return listOf(it.toFunctionDeclarationNode())
    }
    methodDecl()?.let {
        throw EntityNotSupported("Methods")
    }
    throw UnreachableCodeException()
}

private fun GoParser.DeclarationContext.toDeclarationNodes(): List<DeclarationNode> {
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

private fun GoParser.FunctionDeclContext.toFunctionDeclarationNode() = FunctionDeclarationNode(
    pos = Pos(tokenStart = start, tokenStop = stop),
    functionName = functionName()?.IDENTIFIER()?.text ?: throw NullPointerException("functionName cannot be null"),
    signature = TODO(),
    functionBody = TODO()
)
