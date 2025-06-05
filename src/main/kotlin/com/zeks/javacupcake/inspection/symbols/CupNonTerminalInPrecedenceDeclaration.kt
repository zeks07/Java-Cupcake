package com.zeks.javacupcake.inspection.symbols

import com.intellij.codeHighlighting.HighlightDisplayLevel
import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElementVisitor
import com.zeks.javacupcake.bundle.CupBundle
import com.zeks.javacupcake.lang.psi.CupPrecedenceSymbol
import com.zeks.javacupcake.lang.psi.CupVisitor
import com.zeks.javacupcake.references.CupSymbolReference

class CupNonTerminalInPrecedenceDeclaration : CupSymbolsInspection() {
    override fun isEnabledByDefault() = true

    override fun getDefaultLevel() = HighlightDisplayLevel.ERROR

    override fun getDisplayName() = CupBundle.message("cup.inspection.non_terminal_in_precedence_declaration.display.name")

    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor =
        CupNonTerminalInPrecedenceDeclarationInspectionVisitor(holder)
}

private class CupNonTerminalInPrecedenceDeclarationInspectionVisitor(private val holder: ProblemsHolder) : CupVisitor() {
    override fun visitPrecedenceSymbol(symbolInPrecedence: CupPrecedenceSymbol) {
        val symbol = symbolInPrecedence.firstChild ?: return
        val reference = symbol.reference as CupSymbolReference
        if (!reference.isTerminal()) {
            holder.registerProblem(
                symbol,
                CupBundle.message("cup.inspection.non_terminal_in_precedence_declaration.description", symbol.text),
                ProblemHighlightType.GENERIC_ERROR,
                symbol.textRangeInParent
            )
        }
    }
}
