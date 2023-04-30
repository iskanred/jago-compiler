@file:Suppress("JAVA_MODULE_DOES_NOT_EXPORT_PACKAGE")

package inno.jago.codegen

import inno.jago.ast.model.global.SourceFileNode
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.Label
import org.objectweb.asm.Opcodes.ACC_PUBLIC
import org.objectweb.asm.Opcodes.ACC_STATIC
import org.objectweb.asm.Opcodes.V1_8

class GoCodegen {

    fun compile(root: SourceFileNode, name: String) : ByteArray {
        val cw = ClassWriter(ClassWriter.COMPUTE_FRAMES or ClassWriter.COMPUTE_MAXS)
        cw.visit(V1_8, ACC_PUBLIC, name, null, "java/lang/Object", null)

        val mainMethodWriter = cw.visitMethod(ACC_PUBLIC or ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null)
        mainMethodWriter.visitCode()

        val methodStart = Label()
        val methodEnd = Label()
        mainMethodWriter.visitLabel(methodStart)

//        root.topLevelDecls

        TODO()
    }
}
