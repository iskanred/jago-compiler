package inno.jago.semantic.model

import java.util.*

sealed class SemanticEntity(val type: Type)


sealed class NamedEntity(type: Type, val identifier: String) : SemanticEntity(type) {
    override fun equals(other: Any?): Boolean =
        other != null && other is NamedEntity && other.identifier == identifier

    override fun hashCode(): Int = identifier.hashCode()
}

class FuncEntity(type: Type.FuncType, identifier: String) : NamedEntity(type, identifier) {
    override fun equals(other: Any?): Boolean =
        other != null && other is FuncEntity && other.identifier == identifier
                && (other.type as Type.FuncType).paramTypes == (type as Type.FuncType).paramTypes

    override fun hashCode(): Int = Objects.hash(identifier, (type as Type.FuncType).paramTypes)
}

class StructEntity(type: Type.StructType, identifier: String) : NamedEntity(type, identifier) {
    override fun equals(other: Any?): Boolean =
        other != null && other is StructEntity && other.identifier == identifier
                && (other.type as Type.StructType) == this.type

    override fun hashCode(): Int = Objects.hash(identifier, type as Type.StructType)
}


sealed class ObjectEntity(type: Type, identifier: String) : NamedEntity(type, identifier)

class ConstEntity(type: Type, identifier: String) : ObjectEntity(type, identifier)

class VarEntity(type: Type, identifier: String) : ObjectEntity(type, identifier)


sealed class NoNamedEntity(type: Type) : SemanticEntity(type)

class StatementEntity : NoNamedEntity(Type.UnitType)

class ExpressionEntity(type: Type) : NoNamedEntity(type)

