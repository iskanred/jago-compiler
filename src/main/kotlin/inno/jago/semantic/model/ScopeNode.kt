package inno.jago.semantic.model

import inno.jago.exception.JaGoException
import inno.jago.semantic.EntityAlreadyExistsException
import inno.jago.semantic.ReturnInGlobalScopeException

class ScopeNode(
    val name: String,
    private val parent: ScopeNode?,
    private val expectedReturnType: Type?
) {
    private val table = mutableMapOf<String, SemanticEntity>()

    fun createNewScope(name: String, expectedReturnType: Type? = null): ScopeNode =
        ScopeNode(name = name, parent = this, expectedReturnType = expectedReturnType)

    fun addUniqueEntity(entity: SemanticEntity): SemanticEntity {
        entity.identifier
            ?: throw JaGoException("Only entity with non-null can be added to symbol table")

        val oldEntityType = table[entity.identifier]?.entityType

        return if (oldEntityType == entity.entityType) {
            throw EntityAlreadyExistsException(entity)
        } else {
            table[entity.identifier] = entity
            entity
        }
    }

    /**
     * Searches for visible entities by identifier
     * 'Visible' means in one of ancestor's scope
     */
    fun findVisibleEntity(identifier: String): SemanticEntity? {
        var currentScopeNode: ScopeNode? = this
        while (currentScopeNode != null) {
            if (identifier in currentScopeNode.table) {
                return currentScopeNode.table[identifier]
            }
            currentScopeNode = currentScopeNode.parent
        }
        return null
    }

    fun getExpectedReturnType(): Type? {
        return expectedReturnType ?: parent?.getExpectedReturnType()
    }
}

