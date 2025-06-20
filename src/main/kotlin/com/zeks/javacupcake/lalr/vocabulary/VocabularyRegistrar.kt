package com.zeks.javacupcake.lalr.vocabulary

class VocabularyRegistrar : SymbolSet {
    private val nonTerminals = mutableSetOf<NonTerminal>()
    private val terminals = mutableSetOf<Terminal>()
    private val undefined = mutableSetOf<UndefinedSymbol>()

    private val defined = mutableSetOf<String>()

    fun nonTerminal(name: String, presentation: String = name) =
        NonTerminal(name, presentation).also { symbol ->
            require(symbol.name !in defined) { "Symbol with name ${symbol.name} already exists" }
            nonTerminals.add(symbol)
            defined.add(symbol.name)
        }


    fun terminal(name: String, presentation: String = name) =
        Terminal(name, presentation).also { symbol ->
            require(symbol.name !in defined) { "Symbol with name ${symbol.name} already exists" }
            terminals.add(symbol)
            defined.add(symbol.name)
        }

    fun undefined(name: String, presentation: String = name) =
        UndefinedSymbol(name, presentation).also { symbol ->
            undefined.add(symbol)
        }

    override fun findSymbol(name: String) = nonTerminals.find { it.name == name } ?: terminals.find { it.name == name }

    override fun findNonTerminal(name: String) = nonTerminals.find { it.name == name }

    override operator fun contains(symbol: Symbol?) = symbol in nonTerminals || symbol in terminals || symbol in undefined

    fun toVocabulary() = Vocabulary(nonTerminals.toSet(), terminals.toSet(), undefined.toSet())
}
