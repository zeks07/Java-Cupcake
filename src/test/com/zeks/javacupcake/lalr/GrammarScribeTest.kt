package com.zeks.javacupcake.lalr

import com.intellij.psi.PsiElement
import io.mockk.mockk
import org.junit.Test

class GrammarScribeTest {

    @Test
    fun `test adding non terminals`() {
        val element = mockk<PsiElement>(relaxed = true)
        val scribe = GrammarScribe()
        scribe.nonTerminal("S", element)
        scribe.startingNonTerminal("S")
        val grammar = scribe.toGrammar()

        assert(grammar.vocabulary.nonTerminals.containsKey("S"))
    }

    @Test
    fun `test adding terminals`() {
        val element = mockk<PsiElement>(relaxed = true)
        val scribe = GrammarScribe()
        scribe.nonTerminal("S", element)
        scribe.terminal("a", element)
        scribe.startingNonTerminal("S")
        val grammar = scribe.toGrammar()

        assert(grammar.vocabulary.terminals.containsKey("a"))
    }

    @Test
    fun `test adding a rule`() {
        val element = mockk<PsiElement>(relaxed = true)
        val scribe = GrammarScribe()
        scribe.nonTerminal("S", element)
        scribe.terminal("a", element)
        scribe.production("S", listOf("a", "S"), element)
        scribe.production("S", listOf("a"), element)
        scribe.startingNonTerminal("S")
        val grammar = scribe.toGrammar()

        val symbol = grammar.vocabulary.nonTerminals["S"]

        assert(grammar.rules.keys.contains(symbol))
        assert(grammar.rules[symbol]!!.size == 2)
    }

}