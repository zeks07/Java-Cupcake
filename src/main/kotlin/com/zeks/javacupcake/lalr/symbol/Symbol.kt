package com.zeks.javacupcake.lalr.symbol

sealed class Symbol(
    val name: String,
    val representation: String = name,
    val metadata: SymbolMetadata?,
) {

}