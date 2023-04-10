package inno.jago.ast.type

import inno.jago.entity.Entity
import inno.jago.lexer.Pos

sealed class TypeName<T>(
    pos: Pos,
    val identifier: T
) : Entity(pos = pos)

class TypeSimpleName(
    pos: Pos,
    identifier: String
) : TypeName<String>(
    pos = pos,
    identifier = identifier
)
