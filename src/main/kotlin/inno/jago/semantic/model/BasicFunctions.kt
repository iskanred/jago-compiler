package inno.jago.semantic.model

import inno.jago.common.BOOL_TYPE_NAME
import inno.jago.common.DOUBLE_TYPE_NAME
import inno.jago.common.INT_TYPE_NAME
import inno.jago.common.STRING_TYPE_NAME

val basicFunctionEntities = listOf(
    // print
    FuncEntity(
        type = Type.FuncType(
            paramTypes = listOf(Type.StringType),
            returnType = Type.UnitType
        ),
        identifier = "print"
    ),
    // read
    FuncEntity(
        type = Type.FuncType(
            paramTypes = listOf(),
            returnType = Type.StringType
        ),
        identifier = "read"
    ),
    // int
    FuncEntity(
        type = Type.FuncType(
            paramTypes = listOf(Type.NumberType()),
            returnType = Type.IntegerType
        ),
        identifier = INT_TYPE_NAME
    ),
    // float64
    FuncEntity(
        type = Type.FuncType(
            paramTypes = listOf(Type.NumberType()),
            returnType = Type.DoubleType
        ),
        identifier = DOUBLE_TYPE_NAME
    ),
    // int_to_string
    FuncEntity(
        type = Type.FuncType(
            paramTypes = listOf(Type.IntegerType),
            returnType = Type.StringType
        ),
        identifier = "${INT_TYPE_NAME}_to_$STRING_TYPE_NAME"
    ),
    // float64_to_string
    FuncEntity(
        type = Type.FuncType(
            paramTypes = listOf(Type.DoubleType),
            returnType = Type.StringType
        ),
        identifier = "${DOUBLE_TYPE_NAME}_to_$STRING_TYPE_NAME"
    ),
    // bool_to_string
    FuncEntity(
        type = Type.FuncType(
            paramTypes = listOf(Type.BoolType),
            returnType = Type.StringType
        ),
        identifier = "${BOOL_TYPE_NAME}_to_$STRING_TYPE_NAME"
    )
)
