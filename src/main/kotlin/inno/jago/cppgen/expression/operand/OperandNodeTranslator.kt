package inno.jago.cppgen.expression.operand

import inno.jago.ast.model.expression.unary_expression.ApplicationExpressionNode
import inno.jago.ast.model.expression.unary_expression.ConversionNode
import inno.jago.ast.model.expression.unary_expression.IndexExpressionNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.ExpressionOperandNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.LiteralOperandNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.OperandNameNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.OperandNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.QualifiedIdentifierOperandNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.SimpleIdentifierOperandNode
import inno.jago.common.EntityNotSupportedException
import inno.jago.common.UnreachableCodeException
import inno.jago.cppgen.expression.translateToCode
import inno.jago.cppgen.expression.unary_or_primary_expression.translateToCode

fun OperandNode.translateToCode(): String = when (this) {
    is LiteralOperandNode -> translateToCode()
    is OperandNameNode -> translateToCode()
    is ExpressionOperandNode -> translateToCode()
}

fun OperandNameNode.translateToCode(): String {
    return when(this.name) {
        is SimpleIdentifierOperandNode -> this.name.translateToCode()
        is QualifiedIdentifierOperandNode -> this.name.translateToCode()
    }
}
fun SimpleIdentifierOperandNode.translateToCode(): String {
    return this.identifier
}

// по идее тут надо кидать ошибку, но пока что так
fun QualifiedIdentifierOperandNode.translateToCode(): String {
    throw EntityNotSupportedException("QualifiedIdentifierOperandNode")
}
fun ExpressionOperandNode.translateToCode(): String {
    return expression.translateToCode()
}
