package com.zeks.javacupcake.lalr.symbol

import com.intellij.psi.PsiElement

abstract class Symbol(
    private val identifier: String,
    private val psiElement: PsiElement
) {
    val name get() = identifier
    val element get() = psiElement
}