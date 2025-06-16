package com.zeks.javacupcake.lalr.vocabulary

interface SymbolDelegate {
    fun bindTo(symbol: Symbol)
}