package com.zeks.javacupcake.inspection.lines

import com.intellij.codeHighlighting.HighlightDisplayLevel
import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.PsiFile
import com.intellij.psi.util.PsiTreeUtil
import com.zeks.javacupcake.bundle.CupBundle
import com.zeks.javacupcake.lang.psi.CupVisitor
import com.zeks.javacupcake.inspection.quickfix.InsertPackageDeclarationQuickFix
import com.zeks.javacupcake.lang.psi.CupPackageSpecLine

class CupMissingPackageInspection : CupLinesInspection() {
    override fun isEnabledByDefault() = true

    override fun getDefaultLevel() = HighlightDisplayLevel.WARNING

    override fun getDisplayName() = CupBundle.message("cup.inspection.missing_package.display.name")

    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor =
        CupMissingPackageInspectionVisitor(holder)
}

private class CupMissingPackageInspectionVisitor(private val holder: ProblemsHolder) : CupVisitor() {
    override fun visitFile(file: PsiFile) {
        val packageDeclaration = PsiTreeUtil.findChildrenOfType(file, CupPackageSpecLine::class.java)
        if (packageDeclaration.isNotEmpty()) return
        holder.registerProblem(
            file,
            CupBundle.message("cup.inspection.missing_package.description"),
            ProblemHighlightType.WARNING,
            TextRange(0, 1),
            InsertPackageDeclarationQuickFix()
        )
    }
}
