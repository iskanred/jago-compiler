package inno.jago.converter

import GoParser
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

private fun GoParser.PackageClauseContext.toPackageNode(): PackageNode = PackageNode(
    pos = Pos(tokenStart = start, tokenStop = stop),
    name = packageName().text
)

private fun List<GoParser.ImportDeclContext>.toImportDeclNodes(): List<ImportNode> = flatMap { importDeclContext ->
    var result = mutableListOf<ImportNode>()
    importDeclContext.importSpec().forEach {
        result.add(
            ImportNode(
                pos = Pos(tokenStart = it.start, tokenStop = it.stop),
                alias = it.packageName().text,
                path = it.importPath().text,
                importAll = (it.DOT() != null)
            )
        )
    }

    return@flatMap result
}

private fun List<GoParser.TopLevelDeclContext>.toTopLevelDeclarations(): List<TopLevelDeclNode> = flatMap {
    it.toTopLevelDeclaration()
}

private fun GoParser.TopLevelDeclContext.toTopLevelDeclaration(): List<TopLevelDeclNode> {
    TODO()
}
