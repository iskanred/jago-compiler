package inno.jago.semantic.model

import inno.jago.lexer.Pos
import inno.jago.semantic.FuncEntityAlreadyExistsException
import inno.jago.semantic.NamedEntityAlreadyExistsException

@Suppress("TooManyFunctions")
sealed class ScopeNode(
    val name: String,
    private val parent: ScopeNode?,
) {
    private val objectEntities = mutableMapOf<String, ObjectEntity>()
    private val funcEntities = mutableSetOf<FuncEntity>()

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
        return when(entity) {
            is ObjectEntity -> addUniqueObjectEntity(objectEntity = entity, pos = pos)
            is FuncEntity -> addUniqueFuncEntity(funcEntity = entity, pos = pos)
        }
    }

    private fun addUniqueObjectEntity(objectEntity: ObjectEntity, pos: Pos): ObjectEntity =
        if (
            objectEntity.identifier in objectEntities ||
            funcEntities.any { it.identifier == objectEntity.identifier }
        ) {
            throw NamedEntityAlreadyExistsException(identifier = objectEntity.identifier, pos = pos)
        } else {
            objectEntity.apply {
                objectEntities[identifier] = this
            }
        }

    private fun addUniqueFuncEntity(funcEntity: FuncEntity, pos: Pos): FuncEntity =
        if (funcEntity in funcEntities || funcEntity.identifier in objectEntities) {
            throw FuncEntityAlreadyExistsException(
                identifier = funcEntity.identifier,
                type = funcEntity.type as Type.FuncType,
                pos = pos
            )
        } else {
            funcEntity.apply {
                funcEntities.add(this)
            }
        }

    /**
     * Searches for visible entities by identifier
     * 'Visible' means in one of ancestor's scope
     */
    fun findVisibleObjectEntity(identifier: String): NamedEntity? {
        var currentScopeNode: ScopeNode? = this
        while (currentScopeNode != null) {
            if (identifier in currentScopeNode.objectEntities) {
                return currentScopeNode.objectEntities[identifier]
            }
            currentScopeNode = currentScopeNode.parent
        }
        return null
    }

    fun findVisibleFuncEntities(identifier: String): List<FuncEntity> {
        val funcEntities = mutableListOf<FuncEntity>()
        var currentScopeNode: ScopeNode? = this
        while (currentScopeNode != null) {
            funcEntities += currentScopeNode.funcEntities.filter { it.identifier == identifier }
            currentScopeNode = currentScopeNode.parent
        }
        return funcEntities
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
