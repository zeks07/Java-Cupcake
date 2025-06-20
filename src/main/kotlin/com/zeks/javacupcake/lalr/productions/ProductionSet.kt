package com.zeks.javacupcake.lalr.productions

import com.zeks.javacupcake.lalr.vocabulary.NonTerminal

class ProductionSet(
    val all: Map<NonTerminal, List<Production>>,
    start: NonTerminal,
) {
    val aliveProductions: Map<NonTerminal, List<Production>>

    init {
        markAliveNonTerminals()
        aliveProductions = findAliveProductions()
        markReachableSymbols(start)
    }

    private fun markAliveNonTerminals() {
        val dead = all.keys.toMutableSet()

        do {
            val newAlive = dead
                .filter { nonTerminal ->
                    (all[nonTerminal] ?: emptyList()).any { it.isLeaf() || it.getNonTerminals().all { symbol -> symbol.isAlive } }
                }
                .also { it.forEach { nonTerminal -> nonTerminal.markAsAlive() } }
            dead.removeAll(newAlive)
        } while (newAlive.isNotEmpty())
    }

    private fun findAliveProductions() = all
        .flatMap { it.value }.filter { it.getNonTerminals().all { symbol -> symbol.isAlive } }.groupBy { it.left }

    private fun markReachableSymbols(start: NonTerminal) {
        val queue = ArrayDeque<NonTerminal>().apply { add(start) }

        while (queue.isNotEmpty()) {
            val nonTerminal = queue.removeFirst()
            nonTerminal.markAsReachable()
            val rightHandSides = aliveProductions[nonTerminal] ?: continue
            queue.addAll(rightHandSides
                .flatMap { it.getNonTerminals() }
                .toSet()
                .filterNot { it.isReachable }
            )
        }
    }

    operator fun get(symbol: NonTerminal) = all[symbol] ?: emptyList()
}