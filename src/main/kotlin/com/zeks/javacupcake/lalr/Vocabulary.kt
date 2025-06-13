package com.zeks.javacupcake.lalr

import com.zeks.javacupcake.lalr.symbol.NonTerminal
import com.zeks.javacupcake.lalr.symbol.Terminal

class Vocabulary(
    val nonTerminals: Set<NonTerminal>,
    val terminals: Set<Terminal>,
) {
}