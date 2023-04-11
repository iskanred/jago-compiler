package inno.jago.converter.expression.primary_expression.operand.literal

import inno.jago.EntityNotSupported
import inno.jago.UnreachableCodeException
import inno.jago.ast.expression.unary_expression.primary_expression.operand.literal_operand.BasicLiteralNode
import inno.jago.ast.expression.unary_expression.primary_expression.operand.literal_operand.DoubleLiteralNode
import inno.jago.ast.expression.unary_expression.primary_expression.operand.literal_operand.FunctionLiteralNode
import inno.jago.ast.expression.unary_expression.primary_expression.operand.literal_operand.IntegerLiteralNode
import inno.jago.ast.expression.unary_expression.primary_expression.operand.literal_operand.LiteralNode
import inno.jago.ast.expression.unary_expression.primary_expression.operand.literal_operand.StringLiteralNode
import inno.jago.converter.common.toPos
import inno.jago.converter.statement.toBlockStatementNode
import inno.jago.converter.signature.toSignatureNode

fun GoParser.LiteralContext.toLiteralNode(): LiteralNode {
    basicLit()?.let { return it.toBasicLiteralNode() }
    functionLit()?.let { return it.toFunctionLiteralNode() }
    compositeLit()?.let {  }
    throw UnreachableCodeException()
}

fun GoParser.BasicLitContext.toBasicLiteralNode(): BasicLiteralNode {
    INT_LIT()?.let {
        return IntegerLiteralNode(pos = toPos(), value = it.text)
    }
    FLOAT_LIT()?.let {
        return DoubleLiteralNode(pos = toPos(), value = it.text)
    }
    STRING_LIT()?.let {
        return StringLiteralNode(pos = toPos(), value = it.text)
    }
    RUNE_LIT()?.let {
        throw EntityNotSupported("Rune literals")
    }
    IMAGINARY_LIT()?.let {
        throw EntityNotSupported("Imaginary literals")
    }
    throw UnreachableCodeException()
}

fun GoParser.FunctionLitContext.toFunctionLiteralNode(): FunctionLiteralNode {
    val signature = signature() ?: throw UnreachableCodeException()
    val functionBody = functionBody() ?: throw UnreachableCodeException()

    return FunctionLiteralNode(
        pos = toPos(),
        signature = signature.toSignatureNode(),
        functionBody = functionBody.toBlockStatementNode()
    )
}

fun GoParser.CompositeLitContext.toCompositeLiteralNode():
