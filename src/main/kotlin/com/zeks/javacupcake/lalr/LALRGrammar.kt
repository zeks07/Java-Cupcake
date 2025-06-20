package com.zeks.javacupcake.lalr

import com.zeks.javacupcake.lalr.productions.Production
import com.zeks.javacupcake.lalr.productions.ProductionSet
import com.zeks.javacupcake.lalr.vocabulary.NonTerminal
import com.zeks.javacupcake.lalr.vocabulary.Symbol
import com.zeks.javacupcake.lalr.vocabulary.Terminal
import com.zeks.javacupcake.lalr.vocabulary.UndefinedSymbol
import com.zeks.javacupcake.lalr.vocabulary.Vocabulary

abstract class Grammar(
    val vocabulary: Vocabulary,
    val productions: ProductionSet,
    val start: NonTerminal,
) {
    fun productionsFor(symbol: Symbol) = when (symbol) {
        is Terminal -> emptyList()
        is UndefinedSymbol -> emptyList()
        is NonTerminal -> productions[symbol]
    }

    fun closure(initialState: Set<Item>): Set<Item> {
        val closure = mutableSetOf<Item>().apply { addAll(initialState) }
        val nonTerminals = mutableSetOf<NonTerminal>().apply { addAll(initialState.map { it.nextSymbol() }.filterIsInstance<NonTerminal>()) }
        val queue = ArrayDeque(nonTerminals)
        while (queue.isNotEmpty()) {
            val symbol = queue.removeFirst()
            closure.addAll(productionsFor(symbol).map { Item(it) })
            queue.addAll(closure.map { it.nextSymbol() }.filterIsInstance<NonTerminal>().filter { it !in nonTerminals }.also { nonTerminals.addAll(it) })
        }
        return closure.toSet()
    }
}

abstract class LRGrammar(
    vocabulary: Vocabulary,
    productions: ProductionSet,
    start: NonTerminal,
) : Grammar(vocabulary, productions, start) {

    val states = mutableSetOf<State>()

    fun

}

class LALRGrammar(
    vocabulary: Vocabulary,
    productions: ProductionSet,
    start: NonTerminal,
) : LRGrammar(vocabulary, productions, start) {

    init {

        val endOfFile = Terminal("EOF", "$")
        val initialState = Item(Production(NonTerminal("S'"), listOf(start, endOfFile)))

        println(
            State(
                0,
                setOf(initialState)
            )
        )
    }

}

class Automaton

class MutableAutomaton : Automaton() {
    val states = mutableSetOf<State>()

}

class State(
    val id: Int,
    val closure: Set<Item>
) {
    override fun toString() = "State $id:\n${closure.joinToString("\n")}"
}

class Item private constructor(
    val production: Production,
    position: Int,
) {
    val position = position

    constructor(production: Production) : this(production, 0)

    fun next() = Item(production, position + 1)

    fun nextSymbol(): Symbol {
        if (position >= production.symbols.size) throw IndexOutOfBoundsException("Position is out of bounds")
        return production.symbols[position]
    }

    override fun toString(): String {
        val left = production.left.presentation
        val alpha = production.symbols.take(position).joinToString(" ") { it.presentation }
        val beta = production.symbols.drop(position).joinToString(" ") { it.presentation }
        return "$left → $alpha • $beta"
    }
}

