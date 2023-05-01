package inno.jago.semantic.model

import inno.jago.lexer.Pos
import inno.jago.semantic.SemanticException

/**
 * @property identifier is only for entities with an identifier
 *  (functions, parameters, variables, constants)
 * @property const must be {true} only for constants
 */
data class SemanticEntity(
    val type: Type,
    val pos: Pos,
    val entityType: EntityType,
    val identifier: String? = null,
    val const: Boolean = false
) {
    init {
        if (entityType != EntityType.NO_IDENTIFIER && identifier == null) {
            throw SemanticException("Identifier expected")
        }
    }
}

@Suppress("UNUSED_PARAMETER")
enum class EntityType(docs: String) {
    IMPORT("Imports are shadowed by Var entities"),
    VAR("Var is anything that has identifier excepting Import: functions, vars, consts, params"),
    NO_IDENTIFIER("Expressions and statements")
}
