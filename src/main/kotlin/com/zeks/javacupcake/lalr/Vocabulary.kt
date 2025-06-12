package com.zeks.javacupcake.lalr

import com.zeks.javacupcake.lalr.symbol.NonTerminal
import com.zeks.javacupcake.lalr.symbol.Symbol
import com.zeks.javacupcake.lalr.symbol.Terminal

class Vocabulary(
    symbols: Map<String, Symbol>
) {
    val nonTerminals = symbols.filter { it.value is NonTerminal }
    val terminals = symbols.filter { it.value is Terminal }
}