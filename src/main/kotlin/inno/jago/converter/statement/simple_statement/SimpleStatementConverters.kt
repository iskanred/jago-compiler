package inno.jago.converter.statement.simple_statement

import GoParser
import inno.jago.ast.statement.SimpleStatementNode

fun GoParser.SimpleStmtContext.toSimpleStatementNode(): SimpleStatementNode {
    incDecStmt()?.let {

    }
    assignment()?.let {
        TODO()
    }
    shortVarDecl()?.let {
        TODO()
    }
    expressionStmt()?.let {
        TODO()
    }
}
