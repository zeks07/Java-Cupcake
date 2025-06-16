package com.zeks.javacupcake.lalr.vocabulary

class NonTerminal(
    name: String,
    representation: String = name,
) : Symbol(name, representation) {
    override fun toString() = "<$representation>"
}