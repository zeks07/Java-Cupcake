package com.zeks.javacupcake.lalr.vocabulary

class VocabularyRegistrar : SymbolSet {
    private val nonTerminals = mutableSetOf<NonTerminal>()
    private val terminals = mutableSetOf<Terminal>()

    private val registered = mutableSetOf<String>()

    fun nonTerminal(name: String, presentation: String = name) =
        NonTerminal(name, presentation).also { symbol ->
            require(symbol !in this) { "Symbol with name ${symbol.name} already exists" }
            nonTerminals.add(symbol)
            registered.add(symbol.name)
        }


    fun terminal(name: String, presentation: String = name) =
        Terminal(name, presentation).also { symbol ->
            require(symbol !in this) { "Symbol with name ${symbol.name} already exists" }
            terminals.add(symbol)
            registered.add(symbol.name)
        }


    override operator fun contains(symbol: Symbol) = symbol.name in registered

    fun toVocabulary() = Vocabulary(nonTerminals.toSet(), terminals.toSet())
}
