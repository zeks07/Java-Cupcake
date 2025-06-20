package com.zeks.javacupcake.lalr.vocabulary

class UndefinedSymbol(
    name: String,
    representation: String = name,
) : Symbol(name, representation) {
    override fun toString() = "!$presentation"
}