package com.zeks.javacupcake.lang.psi

import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNamedElement
import com.zeks.javacupcake.lalr.SymbolASTWrapper

abstract class CupNamedTerminal(node: ASTNode) : SymbolASTWrapper(node), PsiNamedElement {

    override fun getName(): String = text

    override fun setName(newName: String): PsiElement? {
        val newNamedTerminal = CupElementFactory.createDeclaredTerminal(project, newName)
        return replace(newNamedTerminal)
    }

}
