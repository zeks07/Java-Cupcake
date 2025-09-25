package com.zeks.javacupcake.lang.psi

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.SyntaxTraverser
import com.intellij.psi.util.PsiTreeUtil
import com.zeks.javacupcake.lang.file.CupFile
import com.zeks.javacupcake.lang.file.CupFileType
import com.zeks.javacupcake.lang.psi.impl.CupNonTerminalDeclarationImpl
import com.zeks.javacupcake.lang.psi.impl.CupPackageSpecImpl
import com.zeks.javacupcake.lang.psi.impl.CupTerminalDeclarationImpl

object CupElementFactory {
    fun createPackageDeclaration(project: Project, packageName: String): CupPackageSpecImpl {
        val file = createFile(project, "package $packageName;")
        return PsiTreeUtil.findChildrenOfType(file, CupPackageSpecImpl::class.java).first()
    }

    fun createNonTerminalDeclaration(project: Project, name: String): CupNonTerminalDeclarationImpl {
        val file = createFile(project, "non terminal $name;")
        return PsiTreeUtil.findChildrenOfType(file, CupNonTerminalDeclarationImpl::class.java).first()
    }

    fun createTerminalDeclaration(project: Project, name: String): CupTerminalDeclarationImpl {
        val file = createFile(project, "terminal $name;")
        return PsiTreeUtil.findChildrenOfType(file, CupTerminalDeclarationImpl::class.java).first()
    }

    fun createDeclaredNonTerminal(project: Project, name: String): CupNamedNonTerminal {
        val file = createFile(project, "non terminal $name;")
        return PsiTreeUtil.findChildrenOfType(file, CupNamedNonTerminal::class.java).first()
    }

    fun createDeclaredTerminal(project: Project, name: String): CupNamedTerminal {
        val file = createFile(project, "terminal $name;")
        return PsiTreeUtil.findChildrenOfType(file, CupNamedTerminal::class.java).first()
    }

    fun createSymbol(project: Project, name: String): CupSymbolElement {
        val file = createFile(project, "$name ::= ;")
        return PsiTreeUtil.findChildrenOfType(file, CupSymbolElement::class.java).first()
    }

    fun createJavaCode(project: Project, text: String): PsiElement {
        return SyntaxTraverser.psiTraverser(createFile(project, text)).first()
    }


    private fun createFile(project: Project, text: String = ""): CupFile =
        PsiFileFactory.getInstance(project)
            .createFileFromText("dummy.cup", CupFileType, text) as CupFile
}
