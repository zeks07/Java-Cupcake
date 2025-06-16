package com.zeks.javacupcake.lalr

import com.zeks.javacupcake.lalr.productions.ProductionBuilder
import com.zeks.javacupcake.lalr.vocabulary.NonTerminal
import com.zeks.javacupcake.lalr.vocabulary.SymbolDelegate
import com.zeks.javacupcake.lalr.vocabulary.VocabularyRegistrar

fun grammar(block: GrammarScribe.() -> Unit): Grammar {
    val scribe = GrammarScribe()
    scribe.block()
    return scribe.toGrammar()
}

class GrammarScribe {
    private val vocabulary = VocabularyRegistrar()
    private var start: NonTerminal? = null
    private val productions = ProductionBuilder(vocabulary)

    fun nonTerminal(name: String, delegate: SymbolDelegate? = null) =
        vocabulary.nonTerminal(name).also {
            delegate?.bindTo(it)
        }

    fun terminal(name: String, presentation: String = name, delegate: SymbolDelegate? = null)
        = vocabulary.terminal(name, presentation).also {
            delegate?.bindTo(it)
        }

    fun production(block: ProductionBuilder.() -> Unit) {
        productions.block()
    }

    fun startWith(nonTerminal: NonTerminal) {
        check(nonTerminal in vocabulary)
        start = nonTerminal
    }

    fun toGrammar(): Grammar {
        check(hasStartingSymbol()) { "Starting element is not defined" }
        return Grammar(vocabulary.toVocabulary(), productions.build(start!!), start!!)
    }

    private fun hasStartingSymbol() = start != null
}
