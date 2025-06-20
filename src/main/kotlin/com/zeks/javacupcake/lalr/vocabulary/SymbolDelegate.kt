package com.zeks.javacupcake.lalr.vocabulary

interface SymbolDelegate {
    var symbol: Symbol?

    fun bindTo(symbol: Symbol)

    fun getName(): String

    fun getRepresentation(): String?
}