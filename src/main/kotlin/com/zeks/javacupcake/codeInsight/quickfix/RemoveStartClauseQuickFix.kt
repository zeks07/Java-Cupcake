package com.zeks.javacupcake.codeInsight.quickfix

import com.intellij.codeInspection.LocalQuickFix
import com.intellij.codeInspection.ProblemDescriptor
import com.intellij.openapi.project.Project

class RemoveStartClauseQuickFix : LocalQuickFix {
    override fun getName() = "Remove start clause"

    override fun getFamilyName() = name

    override fun applyFix(project: Project, descriptor: ProblemDescriptor) = descriptor.psiElement.delete()
}
