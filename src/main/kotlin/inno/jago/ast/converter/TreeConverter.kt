package inno.jago.ast.converter

import GoParser
import inno.jago.common.UnreachableCodeException
import inno.jago.ast.model.global.ImportNode
import inno.jago.ast.model.global.PackageNode
import inno.jago.ast.model.global.SourceFileNode
import inno.jago.ast.model.decl.TopLevelDeclNode
import inno.jago.ast.converter.common.toPos
import inno.jago.ast.converter.declaration.toDeclarationNodes
import inno.jago.ast.converter.declaration.toFunctionDeclarationNode
import inno.jago.common.EntityNotSupportedException

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
        throw EntityNotSupportedException("Methods")
    }
    throw UnreachableCodeException()
}


