package com.zeks.javacupcake.inspection.symbols

import com.intellij.codeHighlighting.HighlightDisplayLevel
import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElementVisitor
import com.zeks.javacupcake.bundle.CupBundle
import com.zeks.javacupcake.inspection.quickfix.InsertNonTerminalDeclarationQuickFix
import com.zeks.javacupcake.inspection.quickfix.InsertTerminalDeclarationQuickFix
import com.zeks.javacupcake.lang.psi.CupSymbol
import com.zeks.javacupcake.lang.psi.CupSymbolElement
import com.zeks.javacupcake.lang.psi.CupVisitor
import com.zeks.javacupcake.references.CupSymbolReference

class CupUndeclaredSymbolInspection : CupSymbolsInspection() {
    override fun isEnabledByDefault() = true

    override fun getDisplayName() = CupBundle.message("cup.inspection.symbols.undeclared_symbol.display.name")

    override fun getDefaultLevel() = HighlightDisplayLevel.ERROR

    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor =
        CupUndeclaredSymbolInspectionVisitor(holder)

}

private class CupUndeclaredSymbolInspectionVisitor(private val holder: ProblemsHolder) : CupVisitor() {
    override fun visitSymbol(symbol: CupSymbol) {
        val reference = symbol.reference as? CupSymbolReference ?: return
        val symbol = symbol.node.psi as? CupSymbolElement ?: return
        if (reference.isDeclared()) return
        when {
            reference.isDefined() -> holder.registerProblem(
                symbol,
                CupBundle.message("cup.inspection.symbols.undeclared_non_terminal.description", symbol.text),
                ProblemHighlightType.WARNING,
                InsertNonTerminalDeclarationQuickFix()
            )
            symbol.isInPrecedenceClause() -> holder.registerProblem(
                symbol,
                CupBundle.message("cup.inspection.symbols.undeclared_terminal.description", symbol.text),
                ProblemHighlightType.WARNING,
                InsertTerminalDeclarationQuickFix(symbol.text)
            )
            else -> holder.registerProblem(
                symbol,
                CupBundle.message("cup.inspection.symbols.undeclared_symbol.description", symbol.text),
                ProblemHighlightType.LIKE_UNKNOWN_SYMBOL,
            )
        }
    }
}
