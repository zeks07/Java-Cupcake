package com.zeks.javacupcake.inspection.symbols

import com.intellij.codeHighlighting.HighlightDisplayLevel
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.PsiFile
import com.intellij.psi.util.PsiTreeUtil
import com.zeks.javacupcake.bundle.CupBundle
import com.zeks.javacupcake.inspection.CupDuplicateElementInspection
import com.zeks.javacupcake.lang.psi.CupDeclaredNonTerminal
import com.zeks.javacupcake.lang.psi.CupDeclaredTerminal

class CupDuplicateDeclarationInspection : CupSymbolsInspection() {
    override fun isEnabledByDefault() = true

    override fun getDefaultLevel() = HighlightDisplayLevel.ERROR

    override fun getDisplayName() = CupBundle.message("cup.inspection.duplicate_declaration.display.name")

    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor =
        CupDuplicateDeclarationInspectionVisitor(holder)
}

private class CupDuplicateDeclarationInspectionVisitor(holder: ProblemsHolder) : CupDuplicateElementInspection(holder, "duplicate_declaration") {
    override fun visitFile(file: PsiFile) {
        val terminals = PsiTreeUtil.findChildrenOfType(file, CupDeclaredTerminal::class.java)
        val nonTerminals = PsiTreeUtil.findChildrenOfType(file, CupDeclaredNonTerminal::class.java)

        inspectElements(terminals + nonTerminals)
    }

    override fun makeArguments(elements: List<PsiElement>): List<Any?> {
        val message = StringBuilder("\n")
        for (symbol in elements) {
            when (symbol) {
                is CupDeclaredTerminal -> message.append("terminal ${symbol.text}\n")
                is CupDeclaredNonTerminal -> message.append("non-terminal ${symbol.text}\n")
            }
        }
        return listOf(message.toString())
    }
}
