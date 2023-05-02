package inno.jago.codogen_to_cpp.type

import inno.jago.semantic.model.Type

fun Type.translateToCode(): String{
    when (this){
        is Type.IntegerType -> {
            return "int"
        }
        is Type.DoubleType -> {
            return "double"
        }
        is Type.StringType -> {
            return "string"
        }
        is Type.BoolType -> {
            return "bool"
        }
        is Type.ArrayType -> {
            return "vector<${this.elementType.translateToCode()}>"
        }
        is Type.PointerType -> {
            return "*${this.baseType.translateToCode()}"
        }
        is Type.TupleType -> {
            var types = ""
            for (i in 0 until this.elementTypes.size) {
                types += this.elementTypes[i].translateToCode()
                if (i != this.elementTypes.size - 1) {
                    types = types + ", "
                }
            }
            return "tuple<${types}>"
        }
        is Type.FuncType -> {
            return "auto"
        }
        else -> {
            return "void"
        }

    }
}
