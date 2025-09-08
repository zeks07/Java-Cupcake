package com.zeks.javacupcake.gotodeclaration

import com.intellij.codeInsight.navigation.actions.GotoDeclarationHandler
import com.intellij.openapi.editor.Editor
import com.intellij.psi.PsiElement
import com.zeks.javacupcake.lang.psi.CupSymbolElement

class CupGoToDeclarationHandler : GotoDeclarationHandler {

    override fun getGotoDeclarationTargets(sourceElement: PsiElement?, offset: Int, edito: Editor?): Array<PsiElement>? {
        if (sourceElement == null) return null
        return when (val element = sourceElement.parent) {
            is CupSymbolElement -> {
                val reference = element.reference
                return reference.multiResolve(false)
                    .mapNotNull { it!!.element }
                    .toTypedArray()
            }
            else -> null
        }
    }

}