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

fun OperandNameNode.translateToCode(): String = when(this.name) {
    is SimpleIdentifierOperandNode -> this.name.translateToCode()
    is QualifiedIdentifierOperandNode -> throw EntityNotSupportedException("packages and structs")
}

fun SimpleIdentifierOperandNode.translateToCode(): String = this.identifier

fun ExpressionOperandNode.translateToCode(): String = expression.translateToCode()
