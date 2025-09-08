package com.zeks.javacupcake.inspection.symbols

import com.intellij.codeHighlighting.HighlightDisplayLevel
import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.util.PsiTreeUtil
import com.zeks.javacupcake.bundle.CupBundle
import com.zeks.javacupcake.lang.psi.CupStartDeclaration
import com.zeks.javacupcake.lang.psi.CupVisitor
import com.zeks.javacupcake.inspection.quickfix.RemoveStartClauseQuickFix
import com.zeks.javacupcake.lang.psi.CupProduction
import com.zeks.javacupcake.references.CupSymbolReference

class CupSymbolInStartClauseInspection : CupSymbolsInspection() {
    override fun isEnabledByDefault() = true

    override fun getDefaultLevel() = HighlightDisplayLevel.ERROR

    override fun getDisplayName() = CupBundle.message("cup.inspection.terminal_in_start_clause.display.name")

    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor =
        CupSymbolInStartClauseInspectionVisitor(holder)
}

private class CupSymbolInStartClauseInspectionVisitor(private val holder: ProblemsHolder) : CupVisitor() {
    override fun visitStartDeclaration(startSpec: CupStartDeclaration) {
        val symbol = startSpec.symbol ?: return
        val reference = symbol.reference as CupSymbolReference

        when {
            reference.isTerminal() -> reportTerminal(symbol, startSpec)
            reference.isNonTerminal() -> checkNonTerminal(symbol, startSpec)
        }

    }

    private fun reportTerminal(symbol: PsiElement, startSpec: PsiElement) =
        holder.registerProblem(
            startSpec,
            CupBundle.message("cup.inspection.terminal_in_start_clause.description", symbol.text),
            ProblemHighlightType.GENERIC_ERROR,
            symbol.textRangeInParent,
            RemoveStartClauseQuickFix()
        )

    private fun checkNonTerminal(symbol: PsiElement, startSpec: PsiElement) {
        val production = PsiTreeUtil.findChildrenOfType(symbol.containingFile, CupProduction::class.java)
            .filter { it.firstChild.text == symbol.text }

        if (production.isNotEmpty()) return

        holder.registerProblem(
            startSpec,
            "Non-terminal ${symbol.text} is not defined in the cup file",
            ProblemHighlightType.GENERIC_ERROR,
            symbol.textRangeInParent
        )
    }
}
