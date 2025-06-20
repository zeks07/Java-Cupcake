package com.zeks.javacupcake.lalr.productions

import com.zeks.javacupcake.lalr.vocabulary.SymbolDelegate

interface ProductionDelegate {
    fun bindTo(production: Production)

    fun getLeft(): SymbolDelegate
}