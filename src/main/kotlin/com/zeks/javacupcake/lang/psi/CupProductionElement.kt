package com.zeks.javacupcake.lang.psi

import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNameIdentifierOwner
import com.zeks.javacupcake.lalr.ProductionASTWrapper
import com.zeks.javacupcake.lalr.vocabulary.SymbolDelegate
import com.zeks.javacupcake.lang.psi.impl.CupSymbolImpl

abstract class CupProductionElement(node: ASTNode) : ProductionASTWrapper(node), PsiNameIdentifierOwner {

    override fun getName(): String = firstChild.text

    override fun getNameIdentifier(): CupSymbolImpl? = firstChild as? CupSymbolImpl

    override fun setName(newName: String): PsiElement? {
        val symbol = firstChild as? CupSymbolElement ?: return null
        val newSymbol = CupElementFactory.createSymbol(project, newName)

        node.replaceChild(symbol.node, newSymbol.node)

        return this
    }

    override fun getLeft() = firstChild.reference?.resolve() as SymbolDelegate
}