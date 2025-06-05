package com.zeks.javacupcake.usage

import com.intellij.find.findUsages.FindUsagesHandler
import com.intellij.find.findUsages.FindUsagesHandlerFactory
import com.intellij.psi.PsiElement
import com.zeks.javacupcake.lang.psi.CupNamedNonTerminal

class CupFindUsagesHandlerFactory : FindUsagesHandlerFactory() {
    override fun canFindUsages(element: PsiElement) = element is CupNamedNonTerminal

    override fun createFindUsagesHandler(
        element: PsiElement,
        forHighlightUsages: Boolean)
    : FindUsagesHandler? = when (element) {
        is CupNamedNonTerminal -> NonTerminalFindUsagesHandler(element)
        else -> null
    }
}