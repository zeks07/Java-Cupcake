package com.zeks.javacupcake.analysis

class Production(
    val left: NonTerminal,
    val right: List<Symbol>,
) {
    init {
        left.addProduction(this)
        for (symbol in right) symbol.isUsed = true
    }
}