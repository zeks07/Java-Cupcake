package com.zeks.javacupcake.lang.manipulators

import com.intellij.openapi.util.TextRange
import com.intellij.psi.AbstractElementManipulator
import com.zeks.javacupcake.lang.psi.CupElementFactory
import com.zeks.javacupcake.lang.psi.CupSymbolElement

class CupSymbolManipulator : AbstractElementManipulator<CupSymbolElement>() {
    override fun handleContentChange(element: CupSymbolElement, range: TextRange, newName: String): CupSymbolElement {
        val newElement = CupElementFactory.createSymbol(element.project, newName)
        element.node.replaceAllChildrenToChildrenOf(newElement.node)
        return element
    }
}