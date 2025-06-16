package com.zeks.javacupcake.lalr.vocabulary

interface SymbolSet {
    operator fun contains(symbol: Symbol): Boolean
}