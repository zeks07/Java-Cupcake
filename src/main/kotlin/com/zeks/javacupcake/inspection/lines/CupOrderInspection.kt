package com.zeks.javacupcake.inspection.lines

import com.intellij.codeHighlighting.HighlightDisplayLevel
import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.PsiFile
import com.intellij.psi.util.PsiTreeUtil
import com.zeks.javacupcake.bundle.CupBundle
import com.zeks.javacupcake.file.CupFileUtil
import com.zeks.javacupcake.lang.psi.CupLine
import com.zeks.javacupcake.lang.psi.CupVisitor

class CupOrderInspection : CupLinesInspection() {
    override fun isEnabledByDefault() = true

    override fun getDefaultLevel() = HighlightDisplayLevel.ERROR

    override fun getDisplayName() = CupBundle.message("cup.inspection.syntax.element_order.display.name")

    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor =
        CupOrderInspectionVisitor(holder)
}

private class CupOrderInspectionVisitor(private val holder: ProblemsHolder) : CupVisitor() {
    override fun visitFile(file: PsiFile) {
        val lines = PsiTreeUtil.findChildrenOfType(file, CupLine::class.java)
        if (lines.isEmpty()) return

        var lastSeenTypeIndex = -1

        for (line in lines) {
            val currentType = CupFileUtil.getElementType(line) ?: continue
            val currentTypeIndex = CupFileUtil.expectedOrder.indexOf(currentType)
            if (currentTypeIndex < lastSeenTypeIndex) {
                val message = CupBundle.message(
                    "cup.inspection.syntax.element_order.description",
                    currentType.toString().lowercase(),
                    CupFileUtil.expectedOrder[lastSeenTypeIndex].toString().lowercase())

                holder.registerProblem(
                    line,
                    message,
                    ProblemHighlightType.GENERIC_ERROR
                )
            } else {
                lastSeenTypeIndex = currentTypeIndex
            }
        }
    }
}
