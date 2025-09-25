package com.zeks.javacupcake.codeInsight.fixes

import com.intellij.codeInspection.LocalQuickFix
import com.intellij.codeInspection.ProblemDescriptor
import com.intellij.ide.util.PsiNavigationSupport
import com.intellij.openapi.project.Project
import com.intellij.psi.util.endOffset
import com.zeks.javacupcake.CupBundle
import com.zeks.javacupcake.lang.file.CupFile
import com.zeks.javacupcake.lang.psi.CupElementFactory

object InsertPackageDeclarationFix : LocalQuickFix {
    override fun getName() = familyName

    override fun getFamilyName() = CupBundle.message("fix.insert_package_declaration.family")

    override fun applyFix(project: Project, descriptor: ProblemDescriptor) {
        val file = descriptor.psiElement.containingFile
        if (file !is CupFile) return

        val packageDeclaration = CupElementFactory.createPackageDeclaration(project, "dummy")
        packageDeclaration.packageName?.delete()
        val offset = packageDeclaration.firstChild.endOffset + 1

        insertLine(file, packageDeclaration)
        PsiNavigationSupport.getInstance().createNavigatable(project, file.virtualFile ?: return, offset).navigate(true)
    }

    override fun startInWriteAction() = true
}