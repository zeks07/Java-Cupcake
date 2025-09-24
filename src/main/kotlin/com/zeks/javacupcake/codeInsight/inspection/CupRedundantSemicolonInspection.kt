package com.zeks.javacupcake.codeInsight.inspection

import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElementVisitor
import com.zeks.javacupcake.CupBundle
import com.zeks.javacupcake.lang.psi.CupOptionalSemicolon
import com.zeks.javacupcake.lang.psi.CupTypes
import com.zeks.javacupcake.lang.psi.CupVisitor

class CupRedundantSemicolonInspection : LocalInspectionTool() {
    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor = object : CupVisitor() {
        override fun visitOptionalSemicolon(element: CupOptionalSemicolon) {
            val semicolon = (element.node.findChildByType(CupTypes.SEMICOLON) ?: return).psi
            holder.registerProblem(
                semicolon,
                CupBundle.message("inspection.redundant_semicolon.description"),
                ProblemHighlightType.LIKE_UNUSED_SYMBOL
            )
        }
    }
}