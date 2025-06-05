package com.zeks.javacupcake.usage

import com.intellij.lang.cacheBuilder.DefaultWordsScanner
import com.intellij.lang.findUsages.FindUsagesProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.tree.TokenSet
import com.zeks.javacupcake.lang.parser.CupLexerAdapter
import com.zeks.javacupcake.lang.psi.CupNamedNonTerminal
import com.zeks.javacupcake.lang.psi.CupTokenSets

class CupFindUsagesProvider : FindUsagesProvider {
    override fun getWordsScanner() = DefaultWordsScanner(
            CupLexerAdapter(),
            CupTokenSets.IDENTIFIERS,
            CupTokenSets.COMMENTS,
            TokenSet.EMPTY)

    override fun canFindUsagesFor(element: PsiElement) = element is CupNamedNonTerminal || element.parent is CupNamedNonTerminal

    override fun getHelpId(element: PsiElement) = null

    override fun getType(element: PsiElement) = when (element) {
        is CupNamedNonTerminal -> "non-terminal"
        else -> ""
    }

    override fun getDescriptiveName(element: PsiElement) = when (element) {
        is CupNamedNonTerminal -> element.name
        else -> ""
    }

    override fun getNodeText(element: PsiElement, p1: Boolean): String = element.text
}