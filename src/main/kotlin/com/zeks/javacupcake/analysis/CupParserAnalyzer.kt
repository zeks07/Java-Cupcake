package com.zeks.javacupcake.analysis

import com.intellij.psi.PsiElement
import com.intellij.psi.PsiRecursiveElementVisitor
import com.zeks.javacupcake.lang.file.CupFile
import com.zeks.javacupcake.lang.psi.elements.CupNamedNonTerminal
import com.zeks.javacupcake.lang.psi.elements.CupNamedSymbol
import com.zeks.javacupcake.lang.psi.elements.CupNamedTerminal
import com.zeks.javacupcake.lang.psi.impl.CupProductionImpl

class CupParserAnalyzer(
    val file: CupFile
) {
    val terminals = mutableSetOf<Terminal>()
    val nonTerminals = mutableSetOf<NonTerminal>()
    val productions = mutableSetOf<Production>()

    init {
        file.accept(object : PsiRecursiveElementVisitor() {
            override fun visitElement(element: PsiElement) =
                when (element) {
                    is CupNamedTerminal -> terminals.add(Terminal(element))
                    is CupNamedNonTerminal -> nonTerminals.add(NonTerminal(element))
                    is CupProductionImpl -> productions.addAll(makeProductions(element))
                    else -> super.visitElement(element)
                } as Unit
        })
    }

    fun makeProductions(production: CupProductionImpl): Collection<Production> {
        val left = (production.symbol.reference?.resolve() as? CupNamedNonTerminal)
            ?.symbol as? NonTerminal ?: return listOf()

        return production.rightHandSideList.map { rule ->
            val symbols = rule.symbolList.mapNotNull {
                (it.reference?.resolve() as? CupNamedSymbol)?.symbol
            }
            Production(left, symbols)
        }
    }
}