package inno.jago.ast.converter.common

import inno.jago.lexer.Pos
import org.antlr.v4.runtime.ParserRuleContext

fun ParserRuleContext.toPos(): Pos {
    return Pos(tokenStart = start, tokenStop = stop)
}
