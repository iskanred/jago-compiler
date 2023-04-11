package inno.jago.converter.expression.primary_expression.operand

import inno.jago.UnreachableCodeException
import inno.jago.ast.expression.unary_expression.primary_expression.operand.ExpressionOperandNode
import inno.jago.ast.expression.unary_expression.primary_expression.operand.OperandIdentifierNode
import inno.jago.ast.expression.unary_expression.primary_expression.operand.OperandNameNode
import inno.jago.ast.expression.unary_expression.primary_expression.operand.OperandNode
import inno.jago.ast.expression.unary_expression.primary_expression.operand.QualifiedIdentifierNode
import inno.jago.converter.common.toPos
import inno.jago.converter.expression.toExpressionNode

fun GoParser.OperandContext.toOperandNode(): OperandNode {
    literal()?.let {

    }

    operandName()?.let {

    }

    expression()?.let {
        return ExpressionOperandNode(
            toPos(),
            expression().toExpressionNode()
        )
    }

    throw UnreachableCodeException()
}


fun GoParser.OperandNameContext.toOperandNameNode(): OperandNameNode {
    return OperandNameNode(
        toPos(),

    )
}

fun GoParser.QualifiedIdentContext.toQualifiedIdentNode(): QualifiedIdentifierNode {
    packageName()?.let {

    }


}
