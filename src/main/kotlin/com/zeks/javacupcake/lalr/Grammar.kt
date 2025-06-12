package com.zeks.javacupcake.lalr

import com.zeks.javacupcake.lalr.symbol.NonTerminal

class Grammar(
    val vocabulary: Vocabulary,
    val rules: Map<NonTerminal, List<Rule>>,
    val start: NonTerminal,
) {
    val aliveNonTerminals = findAliveNonTerminals()
    val aliveRules = removeDeadTerminals()

    fun findAliveNonTerminals(): Set<NonTerminal> {
        val alive = mutableSetOf<NonTerminal>()
        val dead = rules.keys.toMutableSet()

        do {
            val newAlive = dead.filter { nonTerminal ->
                val ruleList = rules[nonTerminal] ?: emptyList()
                ruleList.any { it.isLeaf() || it.getNonTerminals().all { symbol -> symbol in alive } }
            }
            dead.removeAll(newAlive)
            alive.addAll(newAlive)
        } while (newAlive.isNotEmpty())

        return alive.toSet()
    }

    fun removeDeadTerminals() = rules
        .mapValues { it.value.filter { rule -> rule.left in aliveNonTerminals} }
        .mapValues { it.value.filter { rule -> rule.getNonTerminals().all { symbol -> symbol in aliveNonTerminals}} }
        .filter { it.value.isNotEmpty() }

    fun findReachable(): Set<NonTerminal> {
        val reachable = mutableSetOf(start)
        val queue = ArrayDeque<NonTerminal>().apply { add(start) }

        while (queue.isNotEmpty()) {
            val nonTerminal = queue.removeFirst()
            reachable.add(nonTerminal)
            queue.addAll(aliveRules[nonTerminal]!!
                .flatMap { it.getNonTerminals() }
                .toSet()
                .filter { it !in reachable }
            )
        }

        return reachable.toSet()
    }

    fun removeUnreachable() = aliveRules.filterKeys { it in findReachable() }

}
