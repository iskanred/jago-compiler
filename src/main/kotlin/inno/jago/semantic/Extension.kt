package inno.jago.semantic

import inno.jago.semantic.model.Type

fun <T> List<T>.toType(transform: (T) -> (Type)): Type = if (isEmpty()) {
    Type.UnitType
} else if (size == 1) {
    transform(first())
} else {
    Type.TupleType(elementTypes = map(transform))
}
