package com.zeks.javacupcake.lalr

import com.zeks.javacupcake.lalr.productions.ProductionBuilder
import com.zeks.javacupcake.lalr.vocabulary.NonTerminal
import com.zeks.javacupcake.lalr.vocabulary.SymbolDelegate
import com.zeks.javacupcake.lalr.vocabulary.VocabularyRegistrar

fun grammar(block: GrammarScribe.() -> Unit): LALRGrammar {
    val scribe = GrammarScribe()
    scribe.block()
    return scribe.toGrammar()
}

class GrammarScribe {
    private val vocabulary = VocabularyRegistrar()
    private var start: NonTerminal? = null
    private val productions = ProductionBuilder(vocabulary)

    fun nonTerminal(name: String) =
        vocabulary.nonTerminal(name)

    fun nonTerminal(delegate: SymbolDelegate) =
        vocabulary.nonTerminal(delegate.getName()).also {
            delegate.bindTo(it)
        }

    fun terminal(name: String, presentation: String = name) =
        vocabulary.terminal(name, presentation)

    fun terminal(delegate: SymbolDelegate) =
        vocabulary.terminal(delegate.getName()).also {
            delegate.bindTo(it)
        }

    fun undefinedSymbol(name: String, presentation: String = name) =
        vocabulary.undefined(name, presentation)

    fun undefinedSymbol(delegate: SymbolDelegate) =
        vocabulary.undefined(delegate.getName()).also {
            delegate.bindTo(it)
        }

    fun production(block: ProductionBuilder.() -> Unit) {
        productions.block()
    }

    fun startWith(nonTerminal: NonTerminal) {
        check(nonTerminal in vocabulary)
        start = nonTerminal
    }

    fun startWith(delegate: SymbolDelegate) {
        val symbol = vocabulary.findNonTerminal(delegate.getName()) ?: throw IllegalArgumentException("Symbol ${delegate.getName()} is not defined")
        start = symbol
    }

    fun toGrammar(): LALRGrammar {
        check(hasStartingSymbol()) { "Starting element is not defined" }
        return LALRGrammar(vocabulary.toVocabulary(), productions.build(start!!), start!!)
    }

    private fun hasStartingSymbol() = start != null
}
