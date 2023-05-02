package inno.jago.ast.converter.expression.primary_expression.operand.literal

import inno.jago.ast.ArrayLengthNotIntegerLiteralException
import inno.jago.common.UnreachableCodeException
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.literal_operand.BasicLiteralNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.literal_operand.CompositeLiteralNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.literal_operand.DoubleLiteralNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.literal_operand.ElementNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.literal_operand.ExpressionElementNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.literal_operand.FunctionLiteralNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.literal_operand.IntegerLiteralNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.literal_operand.LiteralNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.literal_operand.LiteralValueElementNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.literal_operand.StringLiteralNode
import inno.jago.ast.model.type.ArrayTypeNode
import inno.jago.ast.converter.common.toPos
import inno.jago.ast.converter.expression.toExpressionNode
import inno.jago.ast.converter.statement.toBlockStatementNode
import inno.jago.ast.converter.signature.toSignatureNode
import inno.jago.ast.converter.type.toTypeNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.LiteralOperandNode
import inno.jago.common.EntityNotSupportedException

@Suppress("ReturnCount")
fun GoParser.LiteralContext.toLiteralNode(): LiteralNode {
    basicLit()?.let { return it.toBasicLiteralNode() }
    functionLit()?.let { return it.toFunctionLiteralNode() }
    compositeLit()?.let { return it.toCompositeLiteralNode() }
    throw UnreachableCodeException()
}

@Suppress("ReturnCount", "ThrowsCount")
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
        throw EntityNotSupportedException("Rune literals")
    }
    IMAGINARY_LIT()?.let {
        throw EntityNotSupportedException("Imaginary literals")
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


fun GoParser.CompositeLitContext.toCompositeLiteralNode() = CompositeLiteralNode(
    pos = toPos(),
    literal = literalType().toArrayTypeNode(),
    literalValue = literalValue().toLiteralValueElementNode()
)

@Suppress("ThrowsCount")
fun GoParser.LiteralTypeContext.toArrayTypeNode(): ArrayTypeNode {
    arrayType()?.let {
        return ArrayTypeNode(
            pos = toPos(),
            length = it.arrayLength().expression().toExpressionNode().let { exprNode ->
                ((exprNode as? LiteralOperandNode)?.literalNode as? IntegerLiteralNode)
                    ?: throw ArrayLengthNotIntegerLiteralException(expressionNode = exprNode)
            },
            elementType = it.elementType().type().toTypeNode()
        )
    }
    structType()?.let {
        throw EntityNotSupportedException("Structures")
    }

    throw UnreachableCodeException()
}

fun GoParser.LiteralValueContext.toLiteralValueElementNode() = LiteralValueElementNode(
    pos = toPos(),
    elements = elementList().keyedElement().map { it.element().toElementNode() }
)

fun GoParser.ElementContext.toElementNode(): ElementNode {
    expression()?.let {
        return ExpressionElementNode(
            pos = toPos(),
            expression = it.toExpressionNode()
        )
    }

    literalValue()?.let {
        return it.toLiteralValueElementNode()
    }

    throw UnreachableCodeException()
}
