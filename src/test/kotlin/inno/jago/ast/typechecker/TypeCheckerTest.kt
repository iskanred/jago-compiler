package inno.jago.ast.typechecker

import inno.jago.common.WrongNumberOfExpressionsException
import inno.jago.createAST
import inno.jago.semantic.SemanticException
import inno.jago.semantic.TypeChecker
import inno.jago.semantic.WrongTypeException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class TypeCheckerTest {
    @Test
    fun `reassign variable to different type`() {
        val typeChecker = TypeChecker(createAST("src/test/resources/tests/type_checker/variable/invalid_type_variable_reassign.go"))
        assertThrows<WrongTypeException> { typeChecker.startTypeCheck() }
    }

    @Test
    fun `call function with invalid number of arguments`() {
        val typeChecker = TypeChecker(createAST("src/test/resources/tests/type_checker/fun/invalid_argument_number.go"))
        assertThrows<SemanticException> { typeChecker.startTypeCheck() }
    }

    @Test
    fun `call function with invalid argument type`() {
        val typeChecker = TypeChecker(createAST("src/test/resources/tests/type_checker/fun/invalid_argument_type.go"))
        assertThrows<WrongTypeException> { typeChecker.startTypeCheck() }
    }

    @Test
    fun `short var decl mismatched type`() {
        val typeChecker = TypeChecker(createAST("src/test/resources/tests/type_checker/if/invalid_if_short_decl_type.go"))
        assertThrows<WrongTypeException> { typeChecker.startTypeCheck() }
    }

    @Test
    fun `non-bool if condition`() {
        val typeChecker = TypeChecker(createAST("src/test/resources/tests/type_checker/if/invalid_if_condition.go"))
        assertThrows<WrongTypeException> { typeChecker.startTypeCheck() }
    }

    @Test
    fun `valid short var decl`() {
        val typeChecker = TypeChecker(createAST("src/test/resources/tests/type_checker/if/valid_if_short_decl_type.go"))
        assertDoesNotThrow { typeChecker.startTypeCheck() }
    }

    @Test
    fun `valid bool if condition`() {
        val typeChecker = TypeChecker(createAST("src/test/resources/tests/type_checker/if/valid_if_condition.go"))
        assertDoesNotThrow { typeChecker.startTypeCheck() }
    }

}
