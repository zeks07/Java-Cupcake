package com.zeks.javacupcake.inspection

import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.PsiFile
import com.zeks.javacupcake.CupBundle
import com.zeks.javacupcake.lang.file.CupFile
import com.zeks.javacupcake.lang.file.hasPackageDeclaration
import com.zeks.javacupcake.lang.psi.CupVisitor

class CupMissingPackageInspection : LocalInspectionTool() {
    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor = object : CupVisitor() {
        override fun visitFile(file: PsiFile) {
            val file = file as? CupFile ?: return
            if (file.hasPackageDeclaration()) return

            val child = file.firstChild ?: return
            val offset = child.textOffset
            holder.registerProblem(
                child,
                CupBundle.message("inspection.missing_package.description"),
                ProblemHighlightType.WARNING,
                TextRange(offset, offset + 1),
            )
        }
    }
}