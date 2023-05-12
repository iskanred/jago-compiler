package inno.jago.cppgen.type

import inno.jago.ast.model.type.TypeNode
import inno.jago.semantic.model.Type
import inno.jago.semantic.model.toType
import java.lang.StringBuilder

fun TypeNode.translateToCode(): String = this.toType().translateToCode()

fun Type.translateToCode(): String = when (this) {
    is Type.IntegerType -> "int"
    is Type.DoubleType -> "double"
    is Type.StringType -> "string"
    is Type.BoolType -> "bool"
    is Type.ArrayType -> "vector <${this.elementType.translateToCode()}>"
    is Type.PointerType -> "*${this.baseType.translateToCode()}"
    is Type.TupleType -> {
        val types = StringBuilder()
        for (i in 0 until this.elementTypes.size) {
            types.append(this.elementTypes[i].translateToCode())
            if (i != this.elementTypes.size - 1) {
                types.append(", ")
            }
        }
        "tuple<${types}>"
    }
    is Type.FuncType -> "auto"
    is Type.StructType -> "struct"
    is Type.NamedType -> name
    else -> "void"
}
