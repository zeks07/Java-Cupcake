package com.zeks.javacupcake.lalr

import com.intellij.psi.PsiElement
import com.zeks.javacupcake.lalr.symbol.NonTerminal
import com.zeks.javacupcake.lalr.symbol.Symbol
import com.zeks.javacupcake.lalr.symbol.Terminal

fun grammar(block: GrammarScribe.() -> Unit): Grammar {
    val scribe = GrammarScribe()
    scribe.block()
    return scribe.toGrammar()
}

class GrammarScribe {
    private val vocabulary = mutableMapOf<String, Symbol>()
    private val rules = mutableListOf<Rule>()
    private var start: NonTerminal? = null

    private val definedElements = mutableSetOf<String>()

    fun nonTerminal(name: String, element: PsiElement) = addSymbol(NonTerminal(name, element))

    fun terminal(name: String, element: PsiElement) = addSymbol(Terminal(name, element))

    private fun addSymbol(symbol: Symbol) {
        check(symbol.name !in definedElements) { "Symbol '${symbol.element.text}' is already defined" }
        definedElements.add(symbol.name)
        vocabulary[symbol.name] = symbol
    }

    fun production(name: String, rightSide: List<String>, element: PsiElement) {
        check(name in vocabulary.keys && rightSide.all { it in vocabulary.keys }) { "Rule symbols are not defined" }
        val nonTerminalSymbol = vocabulary[name]
        if (nonTerminalSymbol !is NonTerminal) throw IllegalStateException("Rule name is not a non-terminal")
        rules.add(Rule(nonTerminalSymbol, rightSide.map { vocabulary[it]!! }, element))
    }

    fun startingNonTerminal(name: String) {
        check(name in definedElements) { "Starting element is not defined" }
        val symbol = vocabulary[name]
        check(symbol is NonTerminal) { "Starting element is not a non-terminal" }
        start = symbol
    }

    fun toGrammar(): Grammar {
        check(hasStartingSymbol()) { "Starting element is not defined" }
        return Grammar(Vocabulary(vocabulary), rules.groupBy { it.left }, start!!)
    }

    private fun hasStartingSymbol() = start != null
}