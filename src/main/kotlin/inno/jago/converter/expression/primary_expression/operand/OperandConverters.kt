package inno.jago.converter.expression.primary_expression.operand

import inno.jago.UnreachableCodeException
import inno.jago.ast.expression.unary_expression.primary_expression.operand.ExpressionOperandNode
import inno.jago.ast.expression.unary_expression.primary_expression.operand.OperandIdentifierNode
import inno.jago.ast.expression.unary_expression.primary_expression.operand.OperandNameNode
import inno.jago.ast.expression.unary_expression.primary_expression.operand.OperandNode
import inno.jago.ast.expression.unary_expression.primary_expression.operand.QualifiedIdentifierNode
import inno.jago.ast.expression.unary_expression.primary_expression.operand.SimpleOperandIdentifierNode
import inno.jago.converter.common.toPos
import inno.jago.converter.expression.toExpressionNode

fun GoParser.OperandContext.toOperandNode(): OperandNode {
    literal()?.let {

    }

    operandName()?.let {
        return it.toOperandNameNode()
    }

    expression()?.let {
        return ExpressionOperandNode(
            pos = toPos(),
            expression = expression().toExpressionNode()
        )
    }

    throw UnreachableCodeException()
}


fun GoParser.OperandNameContext.toOperandNameNode(): OperandNameNode {
    return OperandNameNode(
        pos = toPos(),
        identifier = qualifiedIdent().toOperandIdentifierNode()
    )
}

fun GoParser.QualifiedIdentContext.toOperandIdentifierNode(): OperandIdentifierNode {
    packageName()?.let {
        return QualifiedIdentifierNode(
            pos = toPos(),
            packageName =  it.IDENTIFIER().text,
            identifier = IDENTIFIER().text
        )
    }

    return SimpleOperandIdentifierNode(
        pos = toPos(),
        identifier = IDENTIFIER().text
    )
}
