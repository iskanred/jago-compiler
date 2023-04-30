package inno.jago.semantic.model

import inno.jago.exception.JaGoException
import inno.jago.semantic.EntityAlreadyExistsException

sealed class ScopeNode(
    val name: String,
    private val parent: ScopeNode?,
) {
    private val table = mutableMapOf<String, SemanticEntity>()

    fun createNewFuncScope(name: String, expectedReturnType: Type? = null): ScopeNode =
        FuncScopeNode(name = name, parent = this, expectedReturnType = expectedReturnType)

    fun createNewForScope(name: String): ScopeNode =
        ForScopeNode(name = name, parent = this)

    fun createNewIfScope(name: String): ScopeNode =
        IfScopeNode(name = name, parent = this)

    fun createNewSimpleBlockScope(name: String): ScopeNode =
        SimpleBLockScopeNode(name = name, parent = this)

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
        if (this is FuncScopeNode) {
            return expectedReturnType
        }
        return parent?.getExpectedReturnType()
    }

    fun hasLoopScope(): Boolean {
        if (this is ForScopeNode) {
            return true
        }
        return parent?.hasLoopScope() ?: false
    }

    companion object {
        val globalScopeNode = GlobalScopeNode()
    }
}

class SimpleBLockScopeNode(
    name: String,
    parent: ScopeNode?
) : ScopeNode(
    name = name,
    parent = parent
)

class IfScopeNode(
    name: String,
    parent: ScopeNode?
) : ScopeNode(
    name = name,
    parent = parent
)

class ForScopeNode(
    name: String,
    parent: ScopeNode?
) : ScopeNode(
    name = name,
    parent = parent
)

class FuncScopeNode(
    name: String,
    parent: ScopeNode?,
    val expectedReturnType: Type?
) : ScopeNode(
    name = name,
    parent = parent
)

class GlobalScopeNode : ScopeNode("GLOBAL", null)
