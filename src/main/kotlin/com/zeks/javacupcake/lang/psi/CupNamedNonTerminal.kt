package com.zeks.javacupcake.lang.psi

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNamedElement

abstract class CupNamedNonTerminal(node: ASTNode) : ASTWrapperPsiElement(node), PsiNamedElement {

    override fun getName(): String = firstChild.text

    override fun setName(newName: String): PsiElement? {
        val newNamedNonToken = CupElementFactory.createDeclaredNonTerminal(project, newName)
        return replace(newNamedNonToken)
    }

}
