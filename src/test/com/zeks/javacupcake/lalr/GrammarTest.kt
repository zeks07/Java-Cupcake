package com.zeks.javacupcake.lalr

import com.zeks.javacupcake.lalr.symbol.SymbolMetadata
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Test

fun sampleGrammar() = grammar {
    val element = mockk<SymbolMetadata>(relaxed = true)

    nonTerminal { "S" }
    nonTerminal { "A" }
    nonTerminal { "B" }
    nonTerminal { "C" }
    nonTerminal { "D" }
    nonTerminal { "E" }
    nonTerminal { "F" }

    terminal { "a" showAs "a" with element }
    terminal { "b" with element }
    terminal { "c" showAs "c" }
    terminal { "d" }

    production("S", listOf("a", "A", "B", "C"), element)
    production("S", listOf("b", "C", "E", "S"), element)
    production("S", listOf("a", "E"), element)

    production("A", listOf("b", "E"), element)
    production("A", listOf("S", "C", "D"), element)
    production("A", listOf("d"), element)

    production("B", listOf("d", "F", "S"), element)
    production("B", listOf("a", "B", "C"), element)

    production("C", listOf("a", "E", "S"), element)
    production("C", listOf("b", "E"), element)

    production("D", listOf("a", "A", "C"), element)
    production("D", listOf("d"), element)

    production("E", listOf("a", "C", "E"), element)
    production("E", emptyList(), element)

    production("F", listOf("A", "B"), element)
    production("F", listOf("a", "F"), element)

    startingNonTerminal("S")
}

class GrammarTest {

    @Test
    fun `test alive non terminals`() {
        val aliveNonTerminals = sampleGrammar().findAliveNonTerminals().map { it.name }
        assertEquals(setOf("S", "A", "C", "D", "E"), aliveNonTerminals.toSet())
    }

    @Test
    fun `test removing dead productions`() {
        val aliveNonTerminals = sampleGrammar().removeDeadTerminals()

        val result = aliveNonTerminals.map { it.key.name to it.value.size }.toSet()
        val control = setOf(("S" to 2), ("A" to 3), ("C" to 2), ("D" to 2), ("E" to 2))

        assert(control == result)
    }

    @Test
    fun `test finding reachable non-terminals`() {
        val reachableNonTerminals = sampleGrammar().findReachable().map { it.name }.toSet()

        assertEquals(setOf("S", "C", "E"), reachableNonTerminals)
    }

    @Test
    fun `test complete production`() {
        val reachableNonTerminals = sampleGrammar().removeUnreachable()

        println(reachableNonTerminals)

        assert(reachableNonTerminals.keys.size == 3)
        assert(reachableNonTerminals.values.flatten().size == 6)
    }
}