package inno.jago.converter

import GoParser
import inno.jago.EntityNotSupported
import inno.jago.UnreachableCodeException
import inno.jago.ast.ImportNode
import inno.jago.ast.PackageNode
import inno.jago.ast.SourceFileNode
import inno.jago.ast.decl.TopLevelDeclNode
import inno.jago.converter.common.toPos
import inno.jago.converter.declaration.toDeclarationNodes
import inno.jago.converter.declaration.toFunctionDeclarationNode

fun GoParser.SourceFileContext.toSourceFileNode(): SourceFileNode {
    return SourceFileNode(
        pos = toPos(),
        packageName = packageClause().toPackageNode(),
        importNodes = importDecl().toImportDeclNodes(),
        topLevelDecls = topLevelDecl().toTopLevelDeclarationNodes()
    )
}

fun GoParser.PackageClauseContext.toPackageNode(): PackageNode = PackageNode(
    pos = toPos(),
    name = packageName().text
)

fun List<GoParser.ImportDeclContext>.toImportDeclNodes(): List<ImportNode> = flatMap { importDeclContext ->
    val importSpec = importDeclContext.importSpec()
    importSpec.map {
        ImportNode(
            pos = it.toPos(),
            alias = it.packageName()?.IDENTIFIER()?.text,
            path = it.importPath().STRING_LIT().text,
            importAll = (it.DOT() != null)
        )
    }
}

fun List<GoParser.TopLevelDeclContext>.toTopLevelDeclarationNodes(): List<TopLevelDeclNode> = flatMap {
    it.toTopLevelDeclarationNode()
}

fun GoParser.TopLevelDeclContext.toTopLevelDeclarationNode(): List<TopLevelDeclNode> {
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


