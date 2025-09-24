package com.zeks.javacupcake.lang.psi

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.zeks.javacupcake.lang.references.CupSymbolReference

abstract class CupSymbolElement(node: ASTNode) : ASTWrapperPsiElement(node) {
    override fun getReference() = CupSymbolReference(this)

    fun isInProduction() = parent is CupRightHandSide

    fun isInDefinition() = parent is CupProduction

    fun isInStartClause() = parent is CupStartDeclaration

    fun isInPrecedenceClause() = parent is CupPrecedenceSymbol

    override fun getName(): String = text
}