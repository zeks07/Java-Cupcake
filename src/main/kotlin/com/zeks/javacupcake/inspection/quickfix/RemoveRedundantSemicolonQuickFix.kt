package com.zeks.javacupcake.inspection.quickfix

import com.intellij.codeInspection.LocalQuickFix
import com.intellij.codeInspection.ProblemDescriptor
import com.intellij.openapi.project.Project

class RemoveRedundantSemicolonQuickFix : LocalQuickFix {
    override fun getName() = "Remove redundant semicolon"

    override fun getFamilyName() = name

    override fun applyFix(project: Project, descriptor: ProblemDescriptor) = descriptor.psiElement.delete()
}
