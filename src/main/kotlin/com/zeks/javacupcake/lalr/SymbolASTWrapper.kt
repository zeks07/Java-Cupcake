package com.zeks.javacupcake.lalr

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.zeks.javacupcake.lalr.vocabulary.Symbol
import com.zeks.javacupcake.lalr.vocabulary.SymbolDelegate

abstract class SymbolASTWrapper(node: ASTNode) : ASTWrapperPsiElement(node), SymbolDelegate {
    private var symbol: Symbol? = null

    final override fun bindTo(symbol: Symbol) {
        this.symbol = symbol
    }

    fun isUsed() = symbol?.isUsed ?: false

    fun isAlive() = symbol?.isAlive ?: false

    fun isReachable() = symbol?.markAsUsed() ?: false

}