package com.zeks.javacupcake.inspection

import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.search.searches.ReferencesSearch
import com.zeks.javacupcake.CupBundle
import com.zeks.javacupcake.lang.psi.CupDeclaredNonTerminal
import com.zeks.javacupcake.lang.psi.CupDeclaredTerminal
import com.zeks.javacupcake.lang.psi.CupSymbolElement
import com.zeks.javacupcake.lang.psi.CupVisitor

class CupUnusedSymbolInspection : LocalInspectionTool() {
    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor = object : CupVisitor() {
        override fun visitDeclaredNonTerminal(nonTerminal: CupDeclaredNonTerminal) {
            val query = ReferencesSearch.search(nonTerminal)
            for (reference in query.findAll()) {
                if ((reference.element as CupSymbolElement).isInDefinition()) continue
                return
            }

            holder.registerProblem(
                nonTerminal,
                CupBundle.message("inspection.unused_non_terminal.description", nonTerminal.text),
                ProblemHighlightType.LIKE_UNUSED_SYMBOL,
            )
        }

        override fun visitDeclaredTerminal(terminal: CupDeclaredTerminal) {
            val query = ReferencesSearch.search(terminal)
            for (reference in query.findAll()) {
                if ((reference.element as CupSymbolElement).isInDefinition()) continue
                return
            }

            holder.registerProblem(
                terminal,
                CupBundle.message("inspection.unused_terminal.description", terminal.text),
                ProblemHighlightType.LIKE_UNUSED_SYMBOL,
            )
        }
    }
}