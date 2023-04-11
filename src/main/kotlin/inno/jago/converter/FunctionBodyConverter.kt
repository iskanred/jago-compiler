package inno.jago.converter

import GoParser
import inno.jago.ast.statement.BlockStatementNode

fun GoParser.FunctionBodyContext.toBlockStatementNode(): BlockStatementNode {
    block().statementList().statement().forEach {
        it.simpleStmt()?.let {
            TODO()
        }
        it.block()?.let {
            TODO()
        }
        it.declaration()?.let {
            TODO()
        }
        it.returnStmt()?.let {
            TODO()
        }
        it.breakStmt()?.let {
            TODO()
        }
        it.continueStmt()?.let {
            TODO()
        }
        it.ifStmt()?.let {
            TODO()
        }
        it.forStmt().let {
            TODO()
        }
    }
}
