package com.zeks.javacupcake.inspection

import com.intellij.codeHighlighting.HighlightDisplayLevel
import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.search.searches.ReferencesSearch
import com.zeks.javacupcake.lang.psi.CupDeclaredNonTerminal
import com.zeks.javacupcake.lang.psi.CupSymbolElement
import com.zeks.javacupcake.lang.psi.CupVisitor

class CupUnusedSymbolInspection : CupInspectionTool() {
    override fun isEnabledByDefault() = true

    override fun getDefaultLevel() = HighlightDisplayLevel.WEAK_WARNING

    override fun getGroupPath() = arrayOf(groupDisplayName)

    override fun getDisplayName() = "Unused symbol"

    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor =
        CupUnusedSymbolInspectionVisitor(holder)
}

private class CupUnusedSymbolInspectionVisitor(private val holder: ProblemsHolder) : CupVisitor() {
    override fun visitDeclaredNonTerminal(nonTerminal: CupDeclaredNonTerminal) {
        val query = ReferencesSearch.search(nonTerminal)
        for (reference in query.findAll()) {
            if ((reference.element as CupSymbolElement).isInDefinition()) continue
            return
        }
        holder.registerProblem(
            nonTerminal,
            "Unused symbol",
            ProblemHighlightType.LIKE_UNUSED_SYMBOL,
        )
    }
}