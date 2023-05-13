@file:Suppress("JAVA_MODULE_DOES_NOT_EXPORT_PACKAGE")

package inno.jago.codegen

import inno.jago.ast.model.global.SourceFileNode
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.Label
import org.objectweb.asm.Opcodes.ACC_PUBLIC
import org.objectweb.asm.Opcodes.ACC_STATIC
import org.objectweb.asm.Opcodes.BIPUSH
import org.objectweb.asm.Opcodes.GETSTATIC
import org.objectweb.asm.Opcodes.IADD
import org.objectweb.asm.Opcodes.ILOAD
import org.objectweb.asm.Opcodes.INVOKEVIRTUAL
import org.objectweb.asm.Opcodes.ISTORE
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

        mainMethodWriter.visitIntInsn(BIPUSH, 5);
        mainMethodWriter.visitLocalVariable("x", "I", null, methodStart, methodEnd, 0)
        mainMethodWriter.visitVarInsn(ISTORE, 0)



        mainMethodWriter.visitIntInsn(BIPUSH, 7);
        mainMethodWriter.visitLocalVariable("y", "I", null, methodStart, methodEnd, 1)
        mainMethodWriter.visitVarInsn(ISTORE, 1)

        mainMethodWriter.visitInsn(IADD)

        mainMethodWriter.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;")

        mainMethodWriter.visitVarInsn(ILOAD, 0)
        mainMethodWriter.visitVarInsn(ILOAD, 1)

        mainMethodWriter.visitInsn(IADD)

        mainMethodWriter.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(I)V", false)

        mainMethodWriter.visitLabel(methodEnd)
        mainMethodWriter.visitMaxs(-1, -1)
        cw.visitEnd()
        
        return cw.toByteArray()
    }
}
