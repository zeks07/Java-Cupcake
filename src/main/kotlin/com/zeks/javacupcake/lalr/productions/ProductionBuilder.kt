package com.zeks.javacupcake.lalr.productions

import com.zeks.javacupcake.lalr.vocabulary.NonTerminal
import com.zeks.javacupcake.lalr.vocabulary.Symbol
import com.zeks.javacupcake.lalr.vocabulary.SymbolSet

class ProductionBuilder(val vocabulary: SymbolSet) {
    private lateinit var left: NonTerminal
    private val productions = mutableMapOf<NonTerminal, List<Production>>()

    fun forSymbol(nonTerminal: NonTerminal) {
        left = nonTerminal
    }

    fun symbols(vararg symbols: Symbol, delegate: ProductionDelegate? = null) {
        val symbols = symbols.toList()

        val undefined = symbols.filterNot { it in vocabulary }
        require(undefined.isEmpty()) { "Symbols ${undefined.joinToString { it.name }} are not defined" }

        val production = Production(left, symbols).also {
            symbols.forEach { symbol -> symbol.markAsUsed() }
            delegate?.bindTo(it)
        }
        productions[left] = productions.getOrDefault(left, emptyList()) + production
    }

    fun build(start: NonTerminal) = ProductionSet(productions.toMap(), start)
}