package com.zeks.javacupcake.lalr

import com.intellij.psi.PsiElement
import com.zeks.javacupcake.lalr.symbol.NonTerminal
import com.zeks.javacupcake.lalr.symbol.Symbol
import com.zeks.javacupcake.lalr.symbol.Terminal

class Rule(
    val left: NonTerminal,
    val symbols: List<Symbol>,
    val element: PsiElement,
) {
    fun getFirst() = symbols.first()

    fun isLeaf() = symbols.all { it is Terminal }

    fun getNonTerminals() = symbols.filterIsInstance<NonTerminal>()

    operator fun contains(symbol: Symbol) = symbol in symbols

    operator fun iterator() = symbols.iterator()

    override fun toString(): String {
        return "$left -> ${if (symbols.isNotEmpty()) symbols.joinToString(" ") else "ε"}"
    }
}