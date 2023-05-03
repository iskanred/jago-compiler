package inno.jago.cppgen.expression.operand

import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.ExpressionOperandNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.LiteralOperandNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.OperandNameNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.OperandNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.QualifiedIdentifierOperandNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.SimpleIdentifierOperandNode
import inno.jago.common.EntityNotSupportedException
import inno.jago.cppgen.expression.operand.translateToCode
import inno.jago.cppgen.expression.translateToCode
import inno.jago.cppgen.expression.operand.translateToCode as translateLiteralNodeToCode
import inno.jago.cppgen.expression.operand.translateToCode as translateOperandNameNodeToCode

fun OperandNode.translateToCode(): String = when (this) {
    is LiteralOperandNode -> translateLiteralNodeToCode()
    is OperandNameNode -> translateOperandNameNodeToCode()
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
