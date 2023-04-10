package inno.jago.converter

import GoParser
import inno.jago.EntityNotSupported
import inno.jago.UnreachableCodeException
import inno.jago.ast.ImportNode
import inno.jago.ast.PackageNode
import inno.jago.ast.SourceFileNode
import inno.jago.ast.decl.TopLevelDeclNode
import inno.jago.lexer.Pos

fun GoParser.SourceFileContext.toSourceFile(): SourceFileNode {
    return SourceFileNode(
        pos = Pos(tokenStart = start, tokenStop = stop),
        packageName = packageClause().toPackageNode(),
        importNodes = importDecl().toImportDeclNodes(),
        topLevelDecls = topLevelDecl().toTopLevelDeclarations()
    )
}

fun GoParser.PackageClauseContext.toPackageNode(): PackageNode = PackageNode(
    pos = Pos(tokenStart = start, tokenStop = stop),
    name = packageName().text
)

fun List<GoParser.ImportDeclContext>.toImportDeclNodes(): List<ImportNode> = flatMap { importDeclContext ->
    importDeclContext.importSpec().map {
        ImportNode(
            pos = Pos(tokenStart = it.start, tokenStop = it.stop),
            alias = it.packageName().text,
            path = it.importPath().text,
            importAll = (it.DOT() != null)
        )
    }
}

fun List<GoParser.TopLevelDeclContext>.toTopLevelDeclarations(): List<TopLevelDeclNode> = flatMap {
    it.toTopLevelDeclaration()
}

fun GoParser.TopLevelDeclContext.toTopLevelDeclaration(): List<TopLevelDeclNode> {
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


