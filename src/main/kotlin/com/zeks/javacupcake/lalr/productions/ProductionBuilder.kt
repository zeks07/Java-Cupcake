package com.zeks.javacupcake.lalr.productions

import com.zeks.javacupcake.lalr.vocabulary.NonTerminal
import com.zeks.javacupcake.lalr.vocabulary.Symbol
import com.zeks.javacupcake.lalr.vocabulary.SymbolDelegate
import com.zeks.javacupcake.lalr.vocabulary.SymbolSet

class ProductionBuilder(val vocabulary: SymbolSet) {
    private lateinit var left: NonTerminal
    private val productions = mutableMapOf<NonTerminal, List<Production>>()

    fun forSymbol(nonTerminal: NonTerminal) {
        left = nonTerminal
    }

    fun forSymbol(delegate: SymbolDelegate) {
        val symbol = delegate.symbol
        if (symbol !is NonTerminal) throw IllegalArgumentException("Delegate must be a non-terminal symbol")
        left = symbol
    }

    fun symbolsFromDelegates(symbols: List<SymbolDelegate>) =
        symbols(symbols
            .map { it.symbol ?: throw IllegalArgumentException("Delegate must be a non-terminal symbol") }
        )

    fun symbols(vararg symbols: Symbol) = symbols(symbols.toList())

    fun symbols(symbols: List<Symbol>): Production {
        val undefined = symbols.filterNot { it in vocabulary }
        require(undefined.isEmpty()) { "Symbols ${undefined.joinToString { it.name }} are not declared" }

        val production = Production(left, symbols).also {
            symbols.forEach { symbol -> symbol.markAsUsed() }
        }
        productions[left] = productions.getOrDefault(left, emptyList()) + production
        return production
    }

    fun build(start: NonTerminal) = ProductionSet(productions.toMap(), start)
}