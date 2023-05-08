package inno.jago.ast.converter

import GoLexer
import GoParser
import inno.jago.ast.model.decl.FunctionDeclarationNode
import inno.jago.ast.model.decl.VarDeclarationNode
import inno.jago.ast.model.expression.binary_expression.BinaryExpression
import inno.jago.ast.model.expression.binary_expression.RelationOperator
import inno.jago.ast.model.expression.binary_expression.RelationOperators
import inno.jago.ast.model.expression.unary_expression.ApplicationExpressionNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.LiteralOperandNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.OperandNameNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.SimpleIdentifierOperandNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.literal_operand.BasicLiteralNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.literal_operand.BoolLiteralNode
import inno.jago.ast.model.statement.EmptyStatementNode
import inno.jago.ast.model.statement.ExpressionStatementNode
import inno.jago.ast.model.statement.ForClauseStatementNode
import inno.jago.ast.model.statement.IfStatementNode
import inno.jago.ast.model.statement.ReturnStatementNode
import inno.jago.ast.model.statement.IncDecStatementNode
import inno.jago.ast.model.statement.ShortVarDeclNode
import inno.jago.ast.model.statement.SimpleElseStatementNode
import inno.jago.ast.model.type.IntegerTypeNode
import inno.jago.createAST
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*
import kotlin.test.assertNotNull

class AstConverterTest {

    @Test
    fun `for with if and return`() {
        val astRoot = createAST("src/test/resources/tests/ast/for.go")

        assertEquals("main", astRoot.packageName.name, "Unexpected package name")
        assertEquals(1, astRoot.topLevelDecls.size, "Unexpected number of toplevel decls")
        assertEquals(FunctionDeclarationNode::class.java, astRoot.topLevelDecls[0].javaClass, "Unexpected top level decl")

        val funDecl = astRoot.topLevelDecls[0] as FunctionDeclarationNode
        checkMainFun(funDecl)

        assertEquals(2, funDecl.functionBody.block.size, "Unexpected number of stmts in function block")
        assertEquals(ForClauseStatementNode::class.java, funDecl.functionBody.block[0].javaClass, "Unexpected number of stmts in function block")
        assertEquals(EmptyStatementNode::class.java, funDecl.functionBody.block[1].javaClass, "Unexpected number of stmts in function block")

        val forClause = funDecl.functionBody.block[0] as ForClauseStatementNode

        assertEquals(ShortVarDeclNode::class.java, forClause.initStatementNode?.javaClass, "Unexpected type of for init statement")

        val shortVarDecl = forClause.initStatementNode as ShortVarDeclNode
        assertEquals(1, shortVarDecl.identifierList.size, "Unexpected length of for init statement")
        assertEquals("i", shortVarDecl.identifierList[0], "Unexpected name of variable for init statement")

        assertEquals(BinaryExpression::class.java, forClause.condition?.javaClass, "Unexpected type of for condition")
        val condition = forClause.condition as BinaryExpression
        assertEquals(RelationOperators.LESS, (condition.binaryOperator as RelationOperator).operator, "Unexpected relation operator")
        assertEquals(OperandNameNode::class.java, condition.leftExpression.javaClass, "Unexpected left relation operand")
        assertEquals("i", ((condition.leftExpression as OperandNameNode).name as SimpleIdentifierOperandNode).identifier, "Unexpected left relation operand value")
        assertEquals(LiteralOperandNode::class.java, condition.rightExpression.javaClass, "Unexpected right relation operand")
        assertEquals("3", ((condition.rightExpression as LiteralOperandNode).literalNode as BasicLiteralNode).value, "Unexpected right relation operand value")

        assertEquals(IncDecStatementNode::class.java, forClause.postStatementNode?.javaClass, "Unexpected type of for post statement")
        val post = (forClause.postStatementNode as IncDecStatementNode)
        assertEquals("i", ((post.expression as OperandNameNode).name as SimpleIdentifierOperandNode).identifier, "Unexpected left relation operand value")
        assertEquals(IncDecStatementNode.IncDec.INC, post.type, "Unexpected left relation operand value")


        val block = forClause.block.block

        assertEquals(3, block.size, "unexpected length of statements")
        assertEquals(IfStatementNode::class.java, block[0].javaClass, "unexpected first statement")
        val ifStmt = block[0] as IfStatementNode
        assertEquals(null, ifStmt.simpleStatement, "unexpected simple statement")
        assertEquals(null, ifStmt.elseBranch, "unexpected else branch")
        assertEquals(2, ifStmt.block.block.size, "unexpected size of statements inside of if")
        assertEquals(ReturnStatementNode::class.java, ifStmt.block.block[0].javaClass, "unexpected first statement inside of if")
        assertEquals(EmptyStatementNode::class.java, ifStmt.block.block[1].javaClass, "unexpected second statement inside of if")


        assertEquals(ExpressionStatementNode::class.java, block[1].javaClass, "unexpected type of the second statement")
        val printExpr = block[1] as ExpressionStatementNode
        assertEquals(ApplicationExpressionNode::class.java, printExpr.expression.javaClass, "unexpected type of the second statement")
        val print = printExpr.expression as ApplicationExpressionNode
        assertEquals(OperandNameNode::class.java, print.leftExpression.javaClass, "да показывает же где")
        assertEquals("print", ((print.leftExpression as OperandNameNode).name as SimpleIdentifierOperandNode).identifier)

        assertEquals(1, print.expressions.size)
        assertEquals(LiteralOperandNode::class.java, print.expressions[0].javaClass)
        assertEquals("\"hi!\"", (((print.expressions[0] as LiteralOperandNode).literalNode) as BasicLiteralNode).value)

        assertEquals(EmptyStatementNode::class.java, block[2].javaClass, "unexpected type of the third statement")
    }

    @Test
    fun `empty main fun must have only empty stmt`() {
        val astRoot = createAST("src/test/resources/tests/ast/empty_main.go")

        assertEquals("main", astRoot.packageName.name, "Unexpected package name")
        assertEquals(1, astRoot.topLevelDecls.size, "Unexpected number of toplevel decls")
        assertEquals(FunctionDeclarationNode::class.java, astRoot.topLevelDecls[0].javaClass, "Unexpected top level decl")

        val funDecl = astRoot.topLevelDecls[0] as FunctionDeclarationNode
        checkMainFun(funDecl)

        assertEquals(1, funDecl.functionBody.block.size, "Unexpected number of stmts in function block")
        assertEquals(EmptyStatementNode::class.java, funDecl.functionBody.block[0].javaClass, "Unexpected number of stmts in function block")
    }

    @Test
    fun `if node with decl must contain else branch`() {
        val astRoot = createAST("src/test/resources/tests/ast/simple_if.go")

        assertEquals("main", astRoot.packageName.name, "Unexpected package name")
        assertEquals(1, astRoot.topLevelDecls.size, "Unexpected number of toplevel decls")
        assertEquals(FunctionDeclarationNode::class.java, astRoot.topLevelDecls[0].javaClass, "Unexpected top level decl")

        val funDecl = astRoot.topLevelDecls[0] as FunctionDeclarationNode
        checkMainFun(funDecl)

        assertEquals(2, funDecl.functionBody.block.size, "Unexpected number of stmts in function block")
        assertEquals(IfStatementNode::class.java, funDecl.functionBody.block[0].javaClass, "Expected if stmt in fun block")
        assertEquals(EmptyStatementNode::class.java, funDecl.functionBody.block[1].javaClass, "Expected empty statement in fun block")

        val ifStmtNode = funDecl.functionBody.block[0] as IfStatementNode
        Assertions.assertNotNull(ifStmtNode.simpleStatement, "Expected not null simple stmt in if")
        assertEquals(ShortVarDeclNode::class.java, ifStmtNode.simpleStatement!!.javaClass, "Expected short var decl in if stmt declaration")
        assertEquals("a", (ifStmtNode.simpleStatement!! as ShortVarDeclNode).identifierList[0], "Unexpected variable name")

        assertEquals(LiteralOperandNode::class.java, ifStmtNode.expression.javaClass, "Expected literal operand in if condition expression")
        assertEquals(BoolLiteralNode::class.java, (ifStmtNode.expression as LiteralOperandNode).literalNode.javaClass, "Unexpected literal type in if condition expression")

        assertEquals(2, ifStmtNode.block.block.size, "Unexpected number of stmts in if block")

        assertNotNull(ifStmtNode.elseBranch, "Expected not null else branch")
        assertEquals(SimpleElseStatementNode::class.java, ifStmtNode.elseBranch!!.javaClass, "Expected simple else branch in if")
        assertEquals(2, (ifStmtNode.elseBranch!! as SimpleElseStatementNode).block.block.size, "Unexpected number of stmts in block of simple else")
    }

    @Test
    fun `additional fun must have only return stmts`() {
        val astRoot = createAST("src/test/resources/tests/ast/fun_calling.go")

        assertEquals("main", astRoot.packageName.name, "Unexpected package name")
        assertEquals(2, astRoot.topLevelDecls.size, "Unexpected number of toplevel decls")
        assertEquals(FunctionDeclarationNode::class.java, astRoot.topLevelDecls[0].javaClass, "Unexpected top level decl")
        assertEquals(FunctionDeclarationNode::class.java, astRoot.topLevelDecls[1].javaClass, "Unexpected top level decl")

        val fooFunDecl = astRoot.topLevelDecls[0] as FunctionDeclarationNode
        assertEquals("foo", fooFunDecl.functionName, "Unexpected fun name")
        assertEquals(1, fooFunDecl.signature.parameterNodes.size, "Unexpected number of parameters of fun")
        assertEquals(1, fooFunDecl.signature.resultNode.size, "Unexpected number of returns parameters of fun")
        assertEquals(2, fooFunDecl.functionBody.block.size, "Expected 2 stmt in foo block")
        assertEquals(ReturnStatementNode::class.java, fooFunDecl.functionBody.block[0].javaClass, "Expected return stmt in foo block")

        val mainFunDecl = astRoot.topLevelDecls[1] as FunctionDeclarationNode
        checkMainFun(mainFunDecl)

        assertEquals(2, mainFunDecl.functionBody.block.size, "Unexpected number of stmts in function block")
        assertEquals(ExpressionStatementNode::class.java, mainFunDecl.functionBody.block[0].javaClass, "Expected if stmt in fun block")
        assertEquals(ApplicationExpressionNode::class.java, (mainFunDecl.functionBody.block[0] as ExpressionStatementNode).expression.javaClass, "Expected application expression")

        val applicationNode = (mainFunDecl.functionBody.block[0] as ExpressionStatementNode).expression as ApplicationExpressionNode
        assertEquals(OperandNameNode::class.java, applicationNode.leftExpression.javaClass, "Expected operand name node in application node")
        assertEquals(1, applicationNode.expressions.size, "Unexpected number of params in application node")
        assertEquals(LiteralOperandNode::class.java, applicationNode.expressions[0].javaClass, "Unexpected type of param in application node")
    }

    @Test
    fun `top level var decl`() {
        val astRoot = createAST("src/test/resources/tests/ast/top_level_var.go")

        assertEquals("main", astRoot.packageName.name, "Unexpected package name")
        assertEquals(2, astRoot.topLevelDecls.size, "Unexpected number of toplevel decls")
        assertEquals(VarDeclarationNode::class.java, astRoot.topLevelDecls[0].javaClass, "Unexpected top level decl")
        assertEquals(FunctionDeclarationNode::class.java, astRoot.topLevelDecls[1].javaClass, "Unexpected top level decl")

        val varDecl = astRoot.topLevelDecls[0] as VarDeclarationNode
        assertEquals("a", varDecl.identifier, "Unexpected identifier of var decl")
        assertEquals(IntegerTypeNode::class.java, varDecl.type!!.javaClass, "Unexpected type of var decl")
        assertEquals(LiteralOperandNode::class.java, varDecl.expression!!.javaClass, "Unexpected expression in var decl")

        val mainFunDecl = astRoot.topLevelDecls[1] as FunctionDeclarationNode
        checkMainFun(mainFunDecl)

        assertEquals(1, mainFunDecl.functionBody.block.size, "Unexpected number of stmts in function block")
    }

    private fun checkMainFun(funDecl: FunctionDeclarationNode) {
        assertEquals("main", funDecl.functionName, "Unexpected fun name")
        assertEquals(0, funDecl.signature.parameterNodes.size, "Unexpected number of parameters of fun")
        assertEquals(0, funDecl.signature.resultNode.size, "Unexpected number of returns parameters of fun")
    }

}
