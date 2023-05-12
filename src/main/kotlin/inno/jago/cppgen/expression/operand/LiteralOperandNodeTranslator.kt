package inno.jago.cppgen.expression.operand

import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.LiteralOperandNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.literal_operand.BoolLiteralNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.literal_operand.CompositeLiteralNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.literal_operand.DoubleLiteralNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.literal_operand.FunctionLiteralNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.literal_operand.IntegerLiteralNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.literal_operand.LiteralNode
import inno.jago.ast.model.expression.unary_expression.primary_expression.operand.literal_operand.StringLiteralNode
import inno.jago.ast.model.type.ArrayTypeNode
import inno.jago.ast.model.type.StructTypeNode
import inno.jago.ast.model.type.TypeNameNode
import inno.jago.common.EntityNotSupportedException
import inno.jago.cppgen.declaration.translateToCode
import inno.jago.cppgen.expression.translateToCode
import inno.jago.cppgen.statement.translateToCode
import inno.jago.cppgen.type.translateToCode

fun LiteralOperandNode.translateToCode(): String = this.literalNode.translateToCode()

fun LiteralNode.translateToCode(): String = when (this) {
    is IntegerLiteralNode -> this.intValue.toString()
    is BoolLiteralNode -> this.boolValue.toString()
    is DoubleLiteralNode -> this.doubleValue.toString()
    is StringLiteralNode -> this.value
    is CompositeLiteralNode -> {
        when (this.literal) {
            is ArrayTypeNode -> "vector <${this.literal.elementType.translateToCode()}>" +
                    "{" +
                        this.literalValue.elements.joinToString { it.translateToCode() } +
                    "}"

            is TypeNameNode -> {
                if (this.literalValue.elements.isEmpty()) {
                    this.literal.translateToCode() + "()"
                } else {
                    throw EntityNotSupportedException("Composite literal node childs")
                }
            }
            else -> throw EntityNotSupportedException("Composite literal node childred")
        }
    }

    is FunctionLiteralNode -> {
        "[=](" +
                this.signature.parameterNodes.joinToString { param -> param.translateToCode() } +
        ")" +
        this.functionBody.translateToCode()
    }
}

