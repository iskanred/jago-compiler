package inno.jago.semantic

import inno.jago.common.WrongNumberOfExpressionsException
import inno.jago.createAST
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class TypeCheckerTest {
    @Test
    fun `reassign variable to different type`() {
        val typeChecker = TypeChecker(
            createAST("src/test/resources/tests/type_checker/variable/invalid_type_variable_reassign.go")
        )
        assertThrows<WrongTypeException> { typeChecker.startTypeCheck() }
    }

    @Test
    fun `call function with invalid number of arguments`() {
        val typeChecker = TypeChecker(
            createAST("src/test/resources/tests/type_checker/fun/invalid_argument_number.go")
        )
        assertThrows<SemanticException> { typeChecker.startTypeCheck() }
    }

    @Test
    fun `call function with invalid argument type`() {
        val typeChecker = TypeChecker(
            createAST("src/test/resources/tests/type_checker/fun/invalid_argument_type.go")
        )
        assertThrows<NoSuchFunctionException> { typeChecker.startTypeCheck() }
    }

    @Test
    fun `short var decl mismatched type`() {
        val typeChecker = TypeChecker(
            createAST("src/test/resources/tests/type_checker/if/invalid_if_short_decl_type.go")
        )
        assertThrows<WrongTypeException> { typeChecker.startTypeCheck() }
    }

    @Test
    fun `non-bool if condition`() {
        val typeChecker = TypeChecker(
            createAST("src/test/resources/tests/type_checker/if/invalid_if_condition.go")
        )
        assertThrows<WrongTypeException> { typeChecker.startTypeCheck() }
    }

    @Test
    fun `invalid elems in an array initialisation - string`() {
        val typeChecker = TypeChecker(
            createAST("src/test/resources/tests/type_checker/array/invalid_array_data.go")
        )
        assertThrows<SemanticException> { typeChecker.startTypeCheck() }
    }

    @Test
    fun `invalid elems in an array initialisation - double`() {
        val typeChecker = TypeChecker(
            createAST("src/test/resources/tests/type_checker/array/invalid_array_data_2.go")
        )
        assertThrows<SemanticException> { typeChecker.startTypeCheck() }
    }

    @Test
    fun `non-castable error`() {
        val typeChecker = TypeChecker(
            createAST("src/test/resources/tests/type_checker/conversion/non_castable_error.go")
        )
        assertThrows<NonCastableTypeException> { typeChecker.startTypeCheck() }
    }

    @Test
    fun `values casted correctly`() {
        val typeChecker = TypeChecker(
            createAST("src/test/resources/tests/type_checker/conversion/valid.go")
        )
        assertDoesNotThrow { typeChecker.startTypeCheck() }
    }

    @Test
    fun `implicit cast`() {
        val typeChecker = TypeChecker(
            createAST("src/test/resources/tests/type_checker/conversion/implicit_cast.go")
        )
        assertThrows<WrongTypeException> { typeChecker.startTypeCheck() }
    }

    @Test
    fun `array from function different lengths`() {
        val typeChecker = TypeChecker(createAST("src/test/resources/tests/type_checker/array/invalid_function_type.go"))
        assertThrows<WrongTypeException> { typeChecker.startTypeCheck() }
    }

    @Test
    fun `invalid assignment to array element`() {
        val typeChecker = TypeChecker(createAST("src/test/resources/tests/type_checker/array/invalid_data_type.go"))
        assertThrows<WrongTypeException> { typeChecker.startTypeCheck() }
    }

    @Test
    fun `valid assignment with cast to array element`() {
        val typeChecker = TypeChecker(createAST("src/test/resources/tests/type_checker/array/valid_data_type.go"))
        assertDoesNotThrow { typeChecker.startTypeCheck() }
    }

    @Test
    fun `valid length of an array`() {
        val typeChecker = TypeChecker(createAST("src/test/resources/tests/type_checker/array/valid.go"))
        assertDoesNotThrow { typeChecker.startTypeCheck() }
    }

    @Test
    fun `explicit cast that does nothing`() {
        val typeChecker = TypeChecker(
            createAST("src/test/resources/tests/type_checker/conversion/expilicit_cast_that_does_nothing.go")
        )
        assertDoesNotThrow { typeChecker.startTypeCheck() }
    }

    @Test
    fun `explicit casts`() {
        val typeChecker = TypeChecker(
            createAST("src/test/resources/tests/type_checker/conversion/explicit_casts.go")
        )
        assertDoesNotThrow { typeChecker.startTypeCheck() }
    }

    @Test
    fun `implicit cast that works`() {
        val typeChecker = TypeChecker(
            createAST("src/test/resources/tests/type_checker/conversion/explicit_casts_2.go")
        )
        assertDoesNotThrow { typeChecker.startTypeCheck() }
    }

    @Test
    fun `valid short var decl`() {
        val typeChecker = TypeChecker(
            createAST("src/test/resources/tests/type_checker/if/valid_if_short_decl_type.go")
        )
        assertDoesNotThrow { typeChecker.startTypeCheck() }
    }

    @Test
    fun `valid bool if condition`() {
        val typeChecker = TypeChecker(
            createAST("src/test/resources/tests/type_checker/if/valid_if_condition.go")
        )
        assertDoesNotThrow { typeChecker.startTypeCheck() }
    }

    @Test
    fun `invalid type in var declaration 1`() {
        val typeChecker = TypeChecker(
            createAST("src/test/resources/tests/type_checker/variable/invalid_var_declaration_1.go")
        )
        assertThrows<WrongTypeException> { typeChecker.startTypeCheck() }
    }

    @Test
    fun `invalid type in var declaration 2`() {
        val typeChecker = TypeChecker(
            createAST("src/test/resources/tests/type_checker/variable/invalid_var_declaration_2.go")
        )
        assertThrows<WrongTypeException> { typeChecker.startTypeCheck() }
    }

    @Test
    fun `expected 1 but got 2 types in var declaration`() {
        val typeChecker = TypeChecker(
            createAST("src/test/resources/tests/type_checker/variable/invalid_var_declaration_3.go")
        )
        assertThrows<WrongNumberOfExpressionsException> { typeChecker.startTypeCheck() }
    }

    @Test
    fun `expected 2 but got 1 type in var declaration`() {
        val typeChecker = TypeChecker(
            createAST("src/test/resources/tests/type_checker/variable/invalid_var_declaration_3.go")
        )
        assertThrows<WrongNumberOfExpressionsException> { typeChecker.startTypeCheck() }
    }

    @Test
    fun `valid var declaration with tuple`() {
        val typeChecker = TypeChecker(
            createAST("src/test/resources/tests/type_checker/variable/valid_var_declaration_tuple.go")
        )
        assertDoesNotThrow { typeChecker.startTypeCheck() }
    }

    @Test
    fun `expected 1 but got 0 types in var declaration`() {
        val typeChecker = TypeChecker(
            createAST("src/test/resources/tests/type_checker/variable/invalid_var_declaration_5.go")
        )
        assertThrows<WrongTypeException> { typeChecker.startTypeCheck() }
    }

    /* TODO: test string conversion */

    /* TODO: test for pointers */

    @Test
    fun `invalid type in assignment with pointer dereference`(){
        val typeChecker = TypeChecker(
            createAST("src/test/resources/tests/type_checker/pointer/invalid_pointer_dereference_1.go")
        )
        assertThrows<WrongTypeException> { typeChecker.startTypeCheck() }
    }

    @Test
    fun `valid assignment with pointer dereference`(){
        val typeChecker = TypeChecker(
            createAST("src/test/resources/tests/type_checker/pointer/valid_pointer_dereference_1.go")
        )
        assertDoesNotThrow { typeChecker.startTypeCheck() }
    }
}
