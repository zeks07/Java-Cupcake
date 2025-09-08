package com.zeks.javacupcake.lang.psi

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNameIdentifierOwner
import com.zeks.javacupcake.lang.file.LineType
import com.zeks.javacupcake.lang.psi.impl.CupSymbolImpl

abstract class CupProductionLine(node: ASTNode) : ASTWrapperPsiElement(node), PsiNameIdentifierOwner, CupLine {
    override val lineType
        get() = LineType.PRODUCTION

    override fun getName(): String = firstChild.text

    override fun getNameIdentifier(): CupSymbolImpl? = firstChild as? CupSymbolImpl

    override fun setName(newName: String): PsiElement? {
        val symbol = firstChild as? CupSymbolElement ?: return null
        val newSymbol = CupElementFactory.createSymbol(project, newName)

        node.replaceChild(symbol.node, newSymbol.node)

        return this
    }
}