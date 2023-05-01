package inno.jago.ast.converter.expression.primary_expression.operand

import inno.jago.common.UnreachableCodeException
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.ExpressionOperandNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.LiteralOperandNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.IdentifierOperandNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.OperandNameNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.OperandNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.QualifiedIdentifierNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.QualifiedIdentifierOperandNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.SimpleIdentifierOperandNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.literal_operand.BoolLiteralNode
import inno.jago.ast.converter.common.toPos
import inno.jago.ast.converter.expression.primary_expression.operand.literal.toLiteralNode
import inno.jago.ast.converter.expression.toExpressionNode

fun GoParser.OperandContext.toOperandNode(): OperandNode {
    literal()?.let {
        return LiteralOperandNode(pos = toPos(), literalNode = it.toLiteralNode())
    }
    operandName()?.let {
        val operandName = OperandNameNode(pos = toPos(), name = it.toOperandNameNode())
        val boolLiteral = (operandName.name as? SimpleIdentifierOperandNode).toBooleanLiteralNode()

        if (boolLiteral != null) {
            return LiteralOperandNode(pos = toPos(), literalNode = boolLiteral)
        }
        return operandName
    }
    expression()?.let {
        return ExpressionOperandNode(
            pos = toPos(),
            expression = expression().toExpressionNode()
        )
    }
    throw UnreachableCodeException()
}


fun GoParser.OperandNameContext.toOperandNameNode(): IdentifierOperandNode {
    qualifiedIdent()?.let {
        return QualifiedIdentifierOperandNode(
            pos = toPos(),
            qualifiedIdentifier = it.toQualifiedIdentifierNode()
        )
    }
    IDENTIFIER()?.let {
        return SimpleIdentifierOperandNode(
            pos = toPos(),
            identifier = it.text
        )
    }
    throw UnreachableCodeException()
}

fun GoParser.QualifiedIdentContext.toQualifiedIdentifierNode() = QualifiedIdentifierNode(
    pos = toPos(),
    packageName = packageName().IDENTIFIER().text,
    identifier = IDENTIFIER().text
)

fun SimpleIdentifierOperandNode?.toBooleanLiteralNode(): BoolLiteralNode? = this?.identifier?.let {
    when (it) {
        "false", "true" -> BoolLiteralNode(pos = this.pos, value = it)
        else -> null
    }
}
