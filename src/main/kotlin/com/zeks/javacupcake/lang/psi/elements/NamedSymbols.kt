package com.zeks.javacupcake.lang.psi.elements

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.icons.AllIcons
import com.intellij.lang.ASTNode
import com.intellij.navigation.ItemPresentation
import com.intellij.openapi.util.NlsSafe
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNamedElement
import com.zeks.javacupcake.CupIcons
import com.zeks.javacupcake.lang.psi.factory.CupElementFactory
import javax.swing.Icon

abstract class CupNamedTerminal(node: ASTNode) : ASTWrapperPsiElement(node), PsiNamedElement {

    override fun getName(): String = firstChild.text

    override fun setName(newName: String): PsiElement? {
        val newNamedTerminal = CupElementFactory.createDeclaredTerminal(project, newName)
        return replace(newNamedTerminal)
    }

    override fun getPresentation(): ItemPresentation = object : ItemPresentation {
        override fun getPresentableText(): String = name

        override fun getIcon(unused: Boolean): Icon = AllIcons.Nodes.Field
    }
}

abstract class CupNamedNonTerminal(node: ASTNode) : ASTWrapperPsiElement(node), PsiNamedElement {

    override fun getName(): String = firstChild.text

    override fun setName(newName: String): PsiElement? {
        val newNamedNonToken = CupElementFactory.createDeclaredNonTerminal(project, newName)
        return replace(newNamedNonToken)
    }

    override fun getPresentation(): ItemPresentation = object : ItemPresentation {
        override fun getPresentableText(): String = name

        override fun getIcon(unused: Boolean): Icon = AllIcons.Nodes.AbstractMethod
    }
}
