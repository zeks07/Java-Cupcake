package com.zeks.javacupcake.lalr.vocabulary

class NonTerminal(
    name: String,
    presentation: String = name,
) : Symbol(name, "<$presentation>") {
}