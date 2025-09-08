package com.zeks.javacupcake.lang.psi

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode

abstract class CupTerminalDeclarationLine(node: ASTNode) : ASTWrapperPsiElement(node), CupSymbolDeclaration {
    override val symbolDeclarationType: CupSymbolDeclarationType
        get() = CupSymbolDeclarationType.TERMINAL
}