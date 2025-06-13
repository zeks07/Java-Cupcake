package com.zeks.javacupcake.lalr.symbol

class NonTerminal(
    name: String,
    representation: String = name,
    metadata: SymbolMetadata?,
) : Symbol(name, representation, metadata) {
    override fun toString() = "<$representation>"
}