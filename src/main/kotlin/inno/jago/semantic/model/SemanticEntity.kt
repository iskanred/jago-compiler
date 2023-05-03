package inno.jago.semantic.model

sealed class SemanticEntity(val type: Type)


sealed class NamedEntity(type: Type, val identifier: String) : SemanticEntity(type)

class FuncEntity(type: Type.FuncType, identifier: String) : NamedEntity(type, identifier)


sealed class ObjectEntity(type: Type, identifier: String) : NamedEntity(type, identifier)

class ConstEntity(type: Type, identifier: String) : ObjectEntity(type, identifier)

class VarEntity(type: Type, identifier: String) : ObjectEntity(type, identifier)


sealed class NoNamedEntity(type: Type) : SemanticEntity(type)

class StatementEntity : NoNamedEntity(Type.UnitType)

class ExpressionEntity(type: Type) : NoNamedEntity(type)

