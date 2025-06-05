package com.zeks.javacupcake.inspection

import com.intellij.codeHighlighting.HighlightDisplayLevel
import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.PsiFile
import com.intellij.psi.util.PsiTreeUtil
import com.zeks.javacupcake.file.CupFile
import com.zeks.javacupcake.lang.psi.CupVisitor
import com.zeks.javacupcake.lang.psi.impl.CupProductionImpl
import com.zeks.javacupcake.lang.psi.impl.CupSymbolImpl

class CupUnusedSymbolInspection : CupInspectionTool() {
    override fun isEnabledByDefault() = true

    override fun getDefaultLevel() = HighlightDisplayLevel.WARNING

    override fun getGroupPath() = arrayOf(groupDisplayName)

    override fun getDisplayName() = "Unused symbol"

    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor =
        CupUnusedSymbolInspectionVisitor(holder)
}

private class CupUnusedSymbolInspectionVisitor(private val holder: ProblemsHolder) : CupVisitor() {
    override fun visitFile(file: PsiFile) {
        val analyser = Analyser(file as CupFile)
        analyser.analyze()

//        val terminals = PsiTreeUtil.findChildrenOfType(file, CupNamedTerminal::class.java)
//            .filter { !analyser.found(it)}
//
//        for (terminal in terminals) {
//            holder.registerProblem(
//                terminal,
//                "Unused terminal ${terminal.text}",
//                ProblemHighlightType.LIKE_UNUSED_SYMBOL,
//            )
//        }
//
//        val nonTerminals = PsiTreeUtil.findChildrenOfType(file, CupNamedNonTerminal::class.java)
//            .filter { !analyser.found(it) }
//
//        for (nonTerminal in nonTerminals) {
//            holder.registerProblem(
//                nonTerminal,
//                "Unused non-terminal ${nonTerminal.text}",
//                ProblemHighlightType.LIKE_UNUSED_SYMBOL,
//            )
//        }
//
//        val nonTerminalsInDefinitions = PsiTreeUtil.findChildrenOfType(file, CupProductionImpl::class.java)
//            .map { it.firstChild as CupSymbolImpl }
//            .filter { !analyser.found(it) }
//
//        for (nonTerminal in nonTerminalsInDefinitions) {
//            holder.registerProblem(
//                nonTerminal,
//                "Unused non-terminal ${nonTerminal.text}",
//                ProblemHighlightType.LIKE_UNUSED_SYMBOL,
//            )
//        }

        val symbols = PsiTreeUtil.findChildrenOfType(file, CupProductionImpl::class.java)
            .map { it.firstChild as CupSymbolImpl }
            .filter { !analyser.reached(it) }

        for (symbol in symbols) {
            holder.registerProblem(
                symbol,
                "Unreachable production",
                ProblemHighlightType.WARNING
            )
        }
    }
}