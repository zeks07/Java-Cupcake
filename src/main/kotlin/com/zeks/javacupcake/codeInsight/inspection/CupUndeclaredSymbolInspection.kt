package com.zeks.javacupcake.codeInsight.inspection

import com.intellij.codeHighlighting.HighlightDisplayLevel
import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElementVisitor
import com.zeks.javacupcake.CupBundle
import com.zeks.javacupcake.codeInsight.fixes.InsertSymbolDeclarationFix
import com.zeks.javacupcake.lang.psi.CupSymbol
import com.zeks.javacupcake.lang.psi.base.CupSymbolDeclarationType
import com.zeks.javacupcake.lang.psi.elements.CupSymbolElement
import com.zeks.javacupcake.lang.psi.CupVisitor
import com.zeks.javacupcake.lang.psi.util.isError
import com.zeks.javacupcake.lang.references.CupSymbolReference

class CupUndeclaredSymbolInspection : LocalInspectionTool() {
    override fun getDefaultLevel() = HighlightDisplayLevel.ERROR

    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor = object : CupVisitor() {
        override fun visitSymbol(symbol: CupSymbol) {
            val reference = symbol.reference as? CupSymbolReference ?: return
            val symbol = symbol.node.psi as? CupSymbolElement ?: return
            if (reference.isDeclared()) return
            if (symbol.isError()) return
            if (symbol.isInDefinition() || symbol.isInStartClause() || symbol.isInPrecedenceClause()) return
            holder.registerProblem(
                symbol,
                CupBundle.message("inspection.undeclared_symbol.description", symbol.text),
                ProblemHighlightType.LIKE_UNKNOWN_SYMBOL,
                InsertSymbolDeclarationFix(symbol.text, CupSymbolDeclarationType.TERMINAL),
                InsertSymbolDeclarationFix(symbol.text, CupSymbolDeclarationType.NON_TERMINAL)
            )
        }
    }
}