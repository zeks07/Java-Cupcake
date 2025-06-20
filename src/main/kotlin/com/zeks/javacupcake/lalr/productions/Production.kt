package com.zeks.javacupcake.lalr.productions

import com.zeks.javacupcake.lalr.vocabulary.NonTerminal
import com.zeks.javacupcake.lalr.vocabulary.Symbol
import com.zeks.javacupcake.lalr.vocabulary.Terminal

data class Production(
    val left: NonTerminal,
    val symbols: List<Symbol>,
) {
    fun getFirst() = symbols.first()

    fun isLeaf() = symbols.all { it is Terminal }

    fun getNonTerminals() = symbols.filterIsInstance<NonTerminal>()

    operator fun contains(symbol: Symbol) = symbol in symbols

    operator fun iterator() = symbols.iterator()

    override fun toString() = "$left → ${if (symbols.isNotEmpty()) symbols.joinToString(" ") else "ε"}"
}