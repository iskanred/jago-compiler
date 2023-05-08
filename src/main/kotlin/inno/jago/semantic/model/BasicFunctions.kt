package inno.jago.semantic.model

import inno.jago.common.DOUBLE_TYPE_NAME
import inno.jago.common.INT_TYPE_NAME

val baseTypes: List<Type> = listOf(
    Type.StringType,
    Type.IntegerType,
    Type.DoubleType,
    Type.BoolType
)

val pointerBaseTypes: List<Type.PointerType> = baseTypes.map {
    Type.PointerType(baseType = it)
}

val printFunctionEntities = (baseTypes + pointerBaseTypes).map {
    FuncEntity(
        type = Type.FuncType(
            paramTypes = listOf(it),
            returnType = Type.UnitType
        ),
        identifier = "print"
    )
}

val toStringFunctionEntities = (baseTypes).map {
    FuncEntity(
        type = Type.FuncType(
            paramTypes = listOf(it),
            returnType = Type.StringType
        ),
        identifier = "to_string"
    )
}

val basicFunctionEntities = listOf(
    // int(int | float64)
    FuncEntity(
        type = Type.FuncType(
            paramTypes = listOf(Type.NumberType()),
            returnType = Type.IntegerType
        ),
        identifier = INT_TYPE_NAME
    ),
    // float64(int | float64)
    FuncEntity(
        type = Type.FuncType(
            paramTypes = listOf(Type.NumberType()),
            returnType = Type.DoubleType
        ),
        identifier = DOUBLE_TYPE_NAME
    )
) + printFunctionEntities + toStringFunctionEntities
