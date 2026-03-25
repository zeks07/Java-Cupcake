package com.zeks.javacupcake.analysis

import com.zeks.javacupcake.lang.psi.elements.CupNamedSymbol

sealed class Symbol(
    val psiElement: CupNamedSymbol
) {
    init {
        psiElement.symbol = this
    }

    var isUsed = false

    fun isNonTerminal() = this is NonTerminal
}

class Terminal(psiElement: CupNamedSymbol) : Symbol(psiElement)

class NonTerminal(psiElement: CupNamedSymbol) : Symbol(psiElement) {
    val productions = mutableListOf<Production>()

    fun addProduction(production: Production) = productions.add(production)
}