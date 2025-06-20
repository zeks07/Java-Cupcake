package com.zeks.javacupcake.lalr

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.zeks.javacupcake.lalr.productions.Production
import com.zeks.javacupcake.lalr.productions.ProductionDelegate

abstract class ProductionASTWrapper(node: ASTNode) : ASTWrapperPsiElement(node), ProductionDelegate {
    private var production: Production? = null

    final override fun bindTo(production: Production) {
        this.production = production
    }
}