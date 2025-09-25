package com.zeks.javacupcake.codeInsight.inspection

import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElementVisitor
import com.zeks.javacupcake.CupBundle
import com.zeks.javacupcake.lang.psi.CupDeclaredNonTerminal
import com.zeks.javacupcake.lang.psi.CupDeclaredTerminal
import com.zeks.javacupcake.lang.psi.CupVisitor

class CupShadowNameInspection : LocalInspectionTool() {
    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor = object : CupVisitor() {
        override fun visitDeclaredNonTerminal(nonTerminal: CupDeclaredNonTerminal) {
            if (nonTerminal.text != "error") return
            holder.registerProblem(
                nonTerminal,
                CupBundle.message("inspection.shadow_name.description"),
                ProblemHighlightType.WARNING
            )
        }

        override fun visitDeclaredTerminal(terminal: CupDeclaredTerminal) {
            if (terminal.text != "error") return
            holder.registerProblem(
                terminal,
                CupBundle.message("inspection.shadow_name.description"),
                ProblemHighlightType.WARNING
            )
        }
    }
}