package com.zeks.javacupcake.inspection.lines

import com.intellij.codeHighlighting.HighlightDisplayLevel
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.PsiFile
import com.intellij.psi.util.PsiTreeUtil
import com.zeks.javacupcake.bundle.CupBundle
import com.zeks.javacupcake.inspection.CupDuplicateElementInspection
import com.zeks.javacupcake.lang.psi.CupStartSpec

class CupDuplicateStartInspection : CupLinesInspection() {
    override fun isEnabledByDefault() = true

    override fun getDisplayName() = CupBundle.message("cup.inspection.duplicate_start.display.name")

    override fun getDefaultLevel() = HighlightDisplayLevel.ERROR

    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor =
        CupDuplicateStartInspectionVisitor(holder)
}

private class CupDuplicateStartInspectionVisitor(holder: ProblemsHolder) : CupDuplicateElementInspection(holder, "duplicate_start") {
    override fun visitFile(file: PsiFile) {
        val startClauses = PsiTreeUtil.findChildrenOfType(file, CupStartSpec::class.java)
        inspectElements(startClauses)
    }
}
