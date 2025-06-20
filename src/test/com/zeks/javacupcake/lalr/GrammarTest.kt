package com.zeks.javacupcake.lalr

import com.zeks.javacupcake.lalr.vocabulary.NonTerminal
import io.mockk.mockk
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException

fun sampleGrammar() = grammar {
    val S = nonTerminal("S")
    val A = nonTerminal("A")
    val B = nonTerminal("B")
    val C = nonTerminal("C")
    val D = nonTerminal("D")
    val E = nonTerminal("E")
    val F = nonTerminal("F")

    val a = terminal("a", "a")
    val b = terminal("b")
    val d = terminal("d")

    production {
        forSymbol(S)
        symbols(a, A, B, C)
        symbols(b, C, E, S)
        symbols(a, E)
    }

    production {
        forSymbol(A)
        symbols(b, E)
        symbols(S, C, D)
        symbols(d)
    }

    production {
        forSymbol(B)
        symbols(d, F, S)
        symbols(a, B, C)
    }

    production {
        forSymbol(C)
        symbols(a, E, S)
        symbols(b, E)
    }

    production {
        forSymbol(D)
        symbols(a, A, C)
        symbols(d)
    }

    production {
        forSymbol(E)
        symbols(a, C, E)
        symbols()
    }

    production {
        forSymbol(F)
        symbols(A, B)
        symbols(a, F)
    }

    startWith(S)
}

class GrammarTest {

//    @Test
//    fun `should find alive non terminals`() {
//        val aliveNonTerminals = sampleGrammar().findAliveNonTerminals().map { it.name }
//        assertEquals(setOf("S", "A", "C", "D", "E"), aliveNonTerminals.toSet())
//    }
//
//    @Test
//    fun `should remove dead productions`() {
//        val aliveNonTerminals = sampleGrammar().removeDeadTerminals()
//
//        val result = aliveNonTerminals.map { it.key.name to it.value.size }.toSet()
//        val control = setOf(("S" to 2), ("A" to 3), ("C" to 2), ("D" to 2), ("E" to 2))
//
//        assert(control == result)
//    }
//
//    @Test
//    fun `should find reachable non-terminals`() {
//        val reachableNonTerminals = sampleGrammar().findReachable().map { it.name }.toSet()
//
//        assertEquals(setOf("S", "C", "E"), reachableNonTerminals)
//    }

//    @Test
//    fun `should complete production`() {
//        val reachableNonTerminals = sampleGrammar().reachable()
//
//        println(reachableNonTerminals)
//
//        assert(reachableNonTerminals.size == 3)
//    }

    @Test
    fun `should not accept unregistered symbols`() {
        val unregisteredNonTerminal = NonTerminal("Unregistered", "Unregistered")

        Assert.assertThrows(IllegalArgumentException::class.java) {
            grammar {
                val registeredNonTerminal = nonTerminal("Registered")
                production {
                    forSymbol(registeredNonTerminal)
                    symbols(unregisteredNonTerminal)
                }
            }
        }

    }
}