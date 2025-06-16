package com.zeks.javacupcake.lalr.vocabulary

class Terminal(
    name: String,
    representation: String = name,
) : Symbol(name, representation) {
    override fun toString() = "\"$representation\""
}