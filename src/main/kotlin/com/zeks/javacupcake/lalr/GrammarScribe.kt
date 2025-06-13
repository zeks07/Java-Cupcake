package com.zeks.javacupcake.lalr

import com.intellij.psi.PsiElement
import com.zeks.javacupcake.lalr.symbol.NonTerminal
import com.zeks.javacupcake.lalr.symbol.Symbol
import com.zeks.javacupcake.lalr.symbol.SymbolMetadata
import com.zeks.javacupcake.lalr.symbol.Terminal

fun grammar(block: GrammarScribe.() -> Unit): Grammar {
    val scribe = GrammarScribe()
    scribe.block()
    return scribe.toGrammar()
}

class GrammarScribe {
    private val vocabularyy = mutableMapOf<String, Symbol>()
    private val rules = mutableListOf<Rule>()
    private var start: NonTerminal? = null

    private val definedElements = mutableSetOf<String>()

    private val vocabulary = VocabularyRegistrar()

    fun nonTerminal(block: NonTerminalBuilder.() -> Any) =
        when (val result = NonTerminalBuilder().block()) {
            is SymbolInput -> vocabulary.nonTerminal(result.name, result.presentation, result.metadata)
            is String -> vocabulary.nonTerminal(result)
            else -> throw IllegalArgumentException("")
        }

    fun terminal(block: TerminalBuilder.() -> Any) =
        when (val result = TerminalBuilder().block()) {
            is SymbolInput -> vocabulary.terminal(result.name, result.presentation, result.metadata)
            is String -> vocabulary.terminal(result)
            else -> throw IllegalArgumentException("")
        }

    fun production(name: String, rightSide: List<String>, element: PsiElement) {
        check(name in vocabularyy.keys && rightSide.all { it in vocabularyy.keys }) { "Rule symbols are not defined" }
        val nonTerminalSymbol = vocabularyy[name]
        if (nonTerminalSymbol !is NonTerminal) throw IllegalStateException("Rule name is not a non-terminal")
        rules.add(Rule(nonTerminalSymbol, rightSide.map { vocabularyy[it]!! }, element))
    }

    fun startingNonTerminal(name: String) {
        check(name in definedElements) { "Starting element is not defined" }
        val symbol = vocabularyy[name]
        check(symbol is NonTerminal) { "Starting element is not a non-terminal" }
        start = symbol
    }

    fun toGrammar(): Grammar {
        check(hasStartingSymbol()) { "Starting element is not defined" }
        return Grammar(Vocabulary(vocabularyy), rules.groupBy { it.left }, start!!)
    }

    private fun hasStartingSymbol() = start != null
}

class ProductionBuilder {

}

class NonTerminalBuilder {
    infix fun String.with(metadata: SymbolMetadata) = SymbolInput(this, this, metadata)
}

class TerminalBuilder {
    infix fun String.showAs(presentation: String) = SymbolInput(this, presentation)
    infix fun String.with(metadata: SymbolMetadata) = SymbolInput(this, this, metadata)
    infix fun SymbolInput.with(metadata: SymbolMetadata) = SymbolInput(name, presentation, metadata)
}

data class SymbolInput(
    val name: String,
    val presentation: String = name,
    val metadata: SymbolMetadata? = null,
)

class VocabularyRegistrar {
    private val nonTerminals = mutableSetOf<NonTerminal>()
    private val terminals = mutableSetOf<Terminal>()

    private val registered = mutableSetOf<String>()

    fun nonTerminal(name: String, presentation: String = name, metadata: SymbolMetadata? = null) =
        NonTerminal(name, presentation, metadata).also { nonTerminal ->
            require(!exists(nonTerminal.name)) { "Symbol with name ${nonTerminal.name} already exists" }
            nonTerminals.add(nonTerminal)
        }


    fun terminal(name: String, presentation: String = name, metadata: SymbolMetadata? = null) =
        Terminal(name, presentation, metadata).also { terminal ->
            require(!exists(terminal.name)) { "Symbol with name ${terminal.name} already exists" }
            terminals.add(terminal)
        }


    private fun exists(name: String) = name in registered

    fun toVocabulary() = Vocabulary(nonTerminals.toSet(), terminals.toSet())
}
