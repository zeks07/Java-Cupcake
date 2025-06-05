package com.zeks.javacupcake.inspection.redundantconstructs

import com.intellij.codeHighlighting.HighlightDisplayLevel
import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElementVisitor
import com.zeks.javacupcake.bundle.CupBundle
import com.zeks.javacupcake.lang.psi.CupOptionalSemicolon
import com.zeks.javacupcake.lang.psi.CupTypes
import com.zeks.javacupcake.lang.psi.CupVisitor
import com.zeks.javacupcake.inspection.quickfix.RemoveRedundantSemicolonQuickFix

class CupRedundantSemicolonInspection : CupRedundantConstructsInspection() {
    override fun isEnabledByDefault() = true

    override fun getDisplayName() = CupBundle.message("cup.inspection.redundant_semicolon.display.name")

    override fun getDefaultLevel() = HighlightDisplayLevel.WEAK_WARNING

    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor =
        CupRedundantSemicolonInspectionVisitor(holder)
}

private class CupRedundantSemicolonInspectionVisitor(private val holder: ProblemsHolder) : CupVisitor() {
    override fun visitOptionalSemicolon(element: CupOptionalSemicolon) {
        val semicolon = (element.node.findChildByType(CupTypes.SEMICOLON) ?: return).psi
        holder.registerProblem(
            semicolon,
            CupBundle.message("cup.inspection.redundant_semicolon.description"),
            ProblemHighlightType.LIKE_UNUSED_SYMBOL,
            RemoveRedundantSemicolonQuickFix())
    }
}
