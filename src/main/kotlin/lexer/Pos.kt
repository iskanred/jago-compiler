package lexer

class Pos (
    val line: Long = 0,
    val symbol: Int = 0
) {
    fun report() = print("$line:$symbol")
}