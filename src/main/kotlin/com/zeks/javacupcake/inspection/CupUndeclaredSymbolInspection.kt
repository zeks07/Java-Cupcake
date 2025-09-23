package com.zeks.javacupcake.inspection

import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElementVisitor
import com.zeks.javacupcake.CupBundle
import com.zeks.javacupcake.lang.psi.CupSymbol
import com.zeks.javacupcake.lang.psi.CupSymbolElement
import com.zeks.javacupcake.lang.psi.CupVisitor
import com.zeks.javacupcake.lang.psi.isError
import com.zeks.javacupcake.references.CupSymbolReference

class CupUndeclaredSymbolInspection : LocalInspectionTool() {
    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor = object : CupVisitor() {
        override fun visitSymbol(symbol: CupSymbol) {
            val reference = symbol.reference as? CupSymbolReference ?: return
            val symbol = symbol.node.psi as? CupSymbolElement ?: return
            if (symbol.isError()) return
            if (reference.isDeclared()) return
            when {
                symbol.isInDefinition() || symbol.isInStartClause() -> holder.registerProblem(
                    symbol,
                    CupBundle.message("inspection.undeclared_non_terminal.description", symbol.text),
                    ProblemHighlightType.WARNING,
                )
                symbol.isInPrecedenceClause() -> holder.registerProblem(
                    symbol,
                    CupBundle.message("inspection.undeclared_terminal.description", symbol.text),
                    ProblemHighlightType.WARNING,
                )
                else -> holder.registerProblem(
                    symbol,
                    CupBundle.message("inspection.undeclared_symbol.description", symbol.text),
                    ProblemHighlightType.LIKE_UNKNOWN_SYMBOL,
                )
            }
        }
    }
}