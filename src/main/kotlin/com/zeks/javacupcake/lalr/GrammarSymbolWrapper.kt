package com.zeks.javacupcake.lalr

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.zeks.javacupcake.lalr.symbol.SymbolMetadata

abstract class GrammarSymbolWrapper(node: ASTNode) : ASTWrapperPsiElement(node), SymbolMetadata {
    abstract fun symbolName(): String
}