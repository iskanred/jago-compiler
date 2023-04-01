package inno.jago.visitor

import GoParser
import inno.jago.ast.ImportNode
import inno.jago.ast.PackageNode
import inno.jago.ast.SourceFileNode
import inno.jago.ast.decl.TopLevelDecl
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
    TODO()
}

private fun List<GoParser.TopLevelDeclContext>.toTopLevelDeclarations(): List<TopLevelDecl> = flatMap {
    it.toTopLevelDeclaration()
}

private fun GoParser.TopLevelDeclContext.toTopLevelDeclaration(): List<TopLevelDecl> {
    TODO()
}
