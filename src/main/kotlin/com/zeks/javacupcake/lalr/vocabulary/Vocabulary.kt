package com.zeks.javacupcake.lalr.vocabulary

class Vocabulary (
    val nonTerminals: Set<NonTerminal>,
    val terminals: Set<Terminal>,
    val undefined: Set<UndefinedSymbol> = emptySet()
) : SymbolSet {
    override fun findSymbol(name: String) = nonTerminals.find { it.name == name } ?: terminals.find { it.name == name }

    override fun findNonTerminal(name: String) = nonTerminals.find { it.name == name }

    override fun contains(symbol: Symbol?) = symbol in nonTerminals || symbol in terminals

}