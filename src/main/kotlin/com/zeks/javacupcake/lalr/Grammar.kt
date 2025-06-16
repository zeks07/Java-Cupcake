package com.zeks.javacupcake.lalr

import com.zeks.javacupcake.lalr.productions.ProductionSet
import com.zeks.javacupcake.lalr.vocabulary.NonTerminal
import com.zeks.javacupcake.lalr.vocabulary.Vocabulary

class Grammar(
    val vocabulary: Vocabulary,
    val productions: ProductionSet,
    val start: NonTerminal,
) {
    fun reachable() = productions.all.keys.filter { it.isReachable }

}
