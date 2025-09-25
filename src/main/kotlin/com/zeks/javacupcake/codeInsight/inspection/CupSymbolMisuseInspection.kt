package com.zeks.javacupcake.codeInsight.inspection

import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElementVisitor
import com.zeks.javacupcake.CupBundle
import com.zeks.javacupcake.lang.psi.CupPrecedenceSymbol
import com.zeks.javacupcake.lang.psi.CupProduction
import com.zeks.javacupcake.lang.psi.CupStartDeclaration
import com.zeks.javacupcake.lang.psi.CupVisitor
import com.zeks.javacupcake.lang.references.CupSymbolReference

class CupSymbolMisuseInspection : LocalInspectionTool() {
    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor = object : CupVisitor() {
        override fun visitPrecedenceSymbol(symbol: CupPrecedenceSymbol) {
            val symbol = symbol.firstChild ?: return
            val reference = symbol.reference as? CupSymbolReference ?: return

            if (!reference.isNonTerminal()) return

            holder.registerProblem(
                symbol,
                CupBundle.message("inspection.non_terminal_in_precedence.description", reference.canonicalText),
                ProblemHighlightType.GENERIC_ERROR
            )
        }

        override fun visitStartDeclaration(declaration: CupStartDeclaration) {
            val symbol = declaration.symbol ?: return
            val reference = symbol.reference as? CupSymbolReference ?: return

            if (!reference.isTerminal()) return

            holder.registerProblem(
                symbol,
                CupBundle.message("inspection.terminal_in_start.description", reference.canonicalText),
                ProblemHighlightType.GENERIC_ERROR
            )
        }

        override fun visitProduction(production: CupProduction) {
            val symbol = production.symbol
            val reference = symbol.reference as? CupSymbolReference ?: return

            if (reference.isNonTerminal()) return

            holder.registerProblem(
                symbol,
                CupBundle.message("inspection.terminal_in_left_hand_side)of_production.description", reference.canonicalText),
                ProblemHighlightType.GENERIC_ERROR
            )
        }
    }
}