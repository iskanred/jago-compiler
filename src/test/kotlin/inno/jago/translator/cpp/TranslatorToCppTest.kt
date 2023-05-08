package inno.jago.translator.cpp

import org.junit.jupiter.api.Test
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.stream.Stream
import
class TranslatorToCppTest {

    @ParamerizedTest
    fun `hello world`() {


    }

    companion object {
        private const val BASE_DIR = "src/test/resources/translator/input"

        @JvmStatic
        fun pathStream(): Stream<Path> = getFilesStream(BASE_DIR)

        private fun getFilesStream(path: String) = Files.list(Paths.get(path))
    }
}
