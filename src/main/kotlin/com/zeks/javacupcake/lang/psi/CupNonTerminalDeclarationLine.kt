package com.zeks.javacupcake.lang.psi

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode

abstract class CupNonTerminalDeclarationLine(node: ASTNode) : ASTWrapperPsiElement(node), CupSymbolDeclaration {
    override val symbolDeclarationType: CupSymbolDeclarationType
        get() = CupSymbolDeclarationType.NON_TERMINAL
}