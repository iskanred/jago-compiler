package inno.jago.semantic

import inno.jago.lexer.Pos

data class SemanticEntity(
    val text: String,
    val type: Type,
    val entityType: EntityType,
    val pos: Pos
)

enum class EntityType {
    PARAMETER,
    FUNCTION,
    EXPRESSION,
    PATTERN_VAR
}
