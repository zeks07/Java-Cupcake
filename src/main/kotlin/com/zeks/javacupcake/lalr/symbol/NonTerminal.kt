package com.zeks.javacupcake.lalr.symbol

import com.intellij.psi.PsiElement

class NonTerminal(name: String, element: PsiElement) : Symbol(name, element) {
    override fun toString() = "<$name>"
}