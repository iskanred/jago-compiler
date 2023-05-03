package inno.jago.semantic.model

import inno.jago.lexer.Pos
import inno.jago.semantic.NamedEntityAlreadyExistsException

sealed class ScopeNode(
    val name: String,
    private val parent: ScopeNode?,
) {
    private val table = mutableMapOf<String, NamedEntity>()

    fun createNewFuncScope(functionName: String, expectedReturnType: Type? = null) = FuncScopeNode(
        name = "Function '$functionName' scope inside scope [$name]",
        parent = this,
        expectedReturnType = expectedReturnType
    )

    fun createNewForScope() = ForScopeNode(
        name = "'for' statement scope inside scope [$name]",
        parent = this
    )

    fun createNewIfScope() = IfScopeNode(
        name = "'if' statement scope inside scope [$name]",
        parent = this
    )

    fun createNewSimpleBlockScope() = SimpleBLockScopeNode(
        name = "Simple block scope inside scope [$name]",
        parent = this
    )

    fun addUniqueEntity(entity: NamedEntity, pos: Pos): NamedEntity {
        // ignore "_" identifier
        if (entity.identifier == "_") {
            return entity
        }

        if (entity.identifier in table) {
            throw NamedEntityAlreadyExistsException(identifier = entity.identifier, pos = pos)
        }

        table[entity.identifier] = entity

        return entity
    }

    /**
     * Searches for visible entities by identifier
     * 'Visible' means in one of ancestor's scope
     */
    fun findVisibleEntity(identifier: String): NamedEntity? {
        var currentScopeNode: ScopeNode? = this
        while (currentScopeNode != null) {
            if (identifier in currentScopeNode.table) {
                return currentScopeNode.table[identifier]
            }
            currentScopeNode = currentScopeNode.parent
        }
        return null
    }

    fun findExpectedReturnType(): Type? {
        if (this is FuncScopeNode) {
            return expectedReturnType
        }
        return parent?.findExpectedReturnType()
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

class GlobalScopeNode : ScopeNode("GLOBAL", null) {
    init {
        val startPos = Pos(line = 0, startIndex = 0, stopIndex = 0)
        basicFunctionEntities.forEach {
            addUniqueEntity(entity = it, pos = startPos)
        }
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
