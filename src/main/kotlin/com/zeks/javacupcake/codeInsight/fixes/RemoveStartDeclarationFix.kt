package com.zeks.javacupcake.codeInsight.fixes

import com.intellij.codeInspection.LocalQuickFix
import com.intellij.codeInspection.ProblemDescriptor
import com.intellij.openapi.project.Project
import com.zeks.javacupcake.CupBundle

object RemoveStartDeclarationFix : LocalQuickFix {
    override fun getName() = CupBundle.message("fix.remove_start_declaration")

    override fun getFamilyName() = name

    override fun applyFix(project: Project, descriptor: ProblemDescriptor) = descriptor.psiElement.delete()
}
