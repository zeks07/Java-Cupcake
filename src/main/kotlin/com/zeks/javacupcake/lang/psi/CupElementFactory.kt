package com.zeks.javacupcake.lang.psi

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.util.PsiTreeUtil
import com.zeks.javacupcake.lang.file.CupFile
import com.zeks.javacupcake.lang.file.CupFileType

object CupElementFactory {
    fun createPackageDeclaration(project: Project, packageName: String): CupPackageSpec {
        val file = createFile(project, "package $packageName ;")
        return PsiTreeUtil.findChildrenOfType(file, CupPackageSpec::class.java).first()
    }

    fun createNonTerminalDeclaration(project: Project, name: String): CupNonTerminalDeclaration {
        val file = createFile(project, "non terminal $name ;")
        return PsiTreeUtil.findChildrenOfType(file, CupNonTerminalDeclaration::class.java).first()
    }

    fun createTerminalDeclaration(project: Project, name: String): CupTerminalDeclaration {
        val file = createFile(project, "terminal $name ;")
        return PsiTreeUtil.findChildrenOfType(file, CupTerminalDeclaration::class.java).first()
    }

    fun createDeclaredNonTerminal(project: Project, name: String): CupNamedNonTerminal {
        val file = createFile(project, "non terminal $name ;")
        return PsiTreeUtil.findChildrenOfType(file, CupNamedNonTerminal::class.java).first()
    }

    fun createDeclaredTerminal(project: Project, name: String): CupNamedTerminal {
        val file = createFile(project, "terminal $name ;")
        return PsiTreeUtil.findChildrenOfType(file, CupNamedTerminal::class.java).first()
    }

    fun createSymbol(project: Project, name: String): CupSymbolElement {
        val file = createFile(project, "$name ::= ;")
        return PsiTreeUtil.findChildrenOfType(file, CupSymbolElement::class.java).first()
    }


    private fun createFile(project: Project, text: String = ""): CupFile =
        PsiFileFactory.getInstance(project)
            .createFileFromText("dummy.cup", CupFileType, text) as CupFile
}
