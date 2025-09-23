package com.zeks.javacupcake.inspection

import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElementVisitor
import com.zeks.javacupcake.CupBundle
import com.zeks.javacupcake.lang.psi.CupNonTerminalDeclaration
import com.zeks.javacupcake.lang.psi.CupTerminalDeclaration
import com.zeks.javacupcake.lang.psi.CupVisitor

class CupShadowNameInspection : LocalInspectionTool() {
    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor = object : CupVisitor() {
        override fun visitNonTerminalDeclaration(nonTerminal: CupNonTerminalDeclaration) {
            if (nonTerminal.text != "error") return
            holder.registerProblem(
                nonTerminal,
                CupBundle.message("inspection.non_terminal_shadow_name.description"),
                ProblemHighlightType.WARNING
            )
        }

        override fun visitTerminalDeclaration(terminal: CupTerminalDeclaration) {
            if (terminal.text != "error") return
            holder.registerProblem(
                terminal,
                CupBundle.message("inspection.terminal_shadow_name.description"),
                ProblemHighlightType.WARNING
            )
        }
    }
}