package inno.jago.semantic.model

import inno.jago.exception.JaGoException
import inno.jago.lexer.Pos

/**
 * @property identifier is only for entities with an identifier
 *  (functions, parameters, variables)
 */
data class SemanticEntity(
    val type: Type,
    val pos: Pos,
    val entityType: EntityType,
    val identifier: String? = null,
) {
    init {
        if (entityType != EntityType.EXPRESSION && entityType != EntityType.STATEMENT && identifier == null) {
            throw JaGoException("Identifier expected")
        }
    }
}

enum class EntityType {
    PARAMETER,
    FUNCTION,
    IMPORT,
    VARIABLE,
    CONSTANT,
    EXPRESSION,
    STATEMENT
}
