package com.zeks.javacupcake.lalr.vocabulary

interface SymbolSet {
    fun findSymbol(name: String): Symbol?
    fun findNonTerminal(name: String): NonTerminal?
    operator fun contains(symbol: Symbol?): Boolean
}