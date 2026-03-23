package com.zeks.javacupcake.lang.structure

import com.intellij.ide.projectView.PresentationData
import com.intellij.ide.structureView.StructureViewTreeElement
import com.intellij.ide.util.treeView.smartTree.TreeElement
import com.intellij.navigation.ItemPresentation
import com.intellij.psi.NavigatablePsiElement
import com.intellij.psi.util.PsiTreeUtil
import com.zeks.javacupcake.lang.file.CupFile
import com.zeks.javacupcake.lang.psi.CupProduction
import com.zeks.javacupcake.lang.psi.elements.CupNamedNonTerminal
import com.zeks.javacupcake.lang.psi.elements.CupNamedTerminal
import com.zeks.javacupcake.lang.psi.elements.CupRule
import com.zeks.javacupcake.lang.psi.impl.CupProductionImpl

class CupStructureViewElement(
    val element: NavigatablePsiElement,
) : StructureViewTreeElement {
    override fun getValue(): NavigatablePsiElement = element

    override fun getPresentation(): ItemPresentation = element.presentation ?: PresentationData()

    override fun navigate(requestFocus: Boolean) = element.navigate(requestFocus)
    override fun canNavigate(): Boolean = element.canNavigate()
    override fun canNavigateToSource(): Boolean = element.canNavigateToSource()

    override fun getChildren(): Array<out TreeElement?> {
        if (element !is CupFile) return emptyArray()

        val definedNonTerminals = PsiTreeUtil.findChildrenOfType(element, CupProduction::class.java).map { it.symbol.text to it as CupProductionImpl }
        val nonTerminals = PsiTreeUtil.findChildrenOfType(element, CupNamedNonTerminal::class.java)
            .filter { nonTerminalDeclaration -> nonTerminalDeclaration.name !in definedNonTerminals.map { it.first } }
        val terminals = PsiTreeUtil.findChildrenOfType(element, CupNamedTerminal::class.java)

        val result = mutableListOf<TreeElement>()

        for (nonTerminal in nonTerminals) {
            result.add(CupStructureViewElement(nonTerminal))
        }

        for ((_, production) in definedNonTerminals) {
            result.add(CupStructureViewElement(production))
        }

        for (terminal in terminals) {
            result.add(CupStructureViewElement(terminal))
        }

        return result.toTypedArray()
    }
}