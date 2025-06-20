package com.zeks.javacupcake.analysis

import com.intellij.psi.PsiElement
import com.intellij.psi.util.CachedValueProvider
import com.intellij.psi.util.CachedValuesManager
import com.intellij.psi.util.PsiModificationTracker
import com.intellij.psi.util.PsiTreeUtil
import com.zeks.javacupcake.file.CupFile
import com.zeks.javacupcake.lalr.LALRGrammar
import com.zeks.javacupcake.lalr.grammar
import com.zeks.javacupcake.lalr.vocabulary.SymbolDelegate
import com.zeks.javacupcake.lang.psi.CupNamedNonTerminal
import com.zeks.javacupcake.lang.psi.CupNamedTerminal
import com.zeks.javacupcake.lang.psi.impl.CupProductionImpl
import com.zeks.javacupcake.lang.psi.impl.CupRightHandSideImpl
import com.zeks.javacupcake.lang.psi.impl.CupSymbolImpl

object FileAnalyzer {

    private val fileProductions = mutableListOf<CupProductionImpl>()
    private val visitedNames = mutableSetOf<String>()

    fun getGrammar(symbol: PsiElement) = getResults(symbol.containingFile as CupFile)

    private fun getResults(file: CupFile): LALRGrammar = CachedValuesManager.getCachedValue(file) {
        CachedValueProvider.Result.create(buildGrammar(file), PsiModificationTracker.MODIFICATION_COUNT)
    }

    private fun buildGrammar(file: CupFile): LALRGrammar {
        val nonTerminals = PsiTreeUtil.findChildrenOfType(file, CupNamedNonTerminal::class.java).toSet()
        val terminals = PsiTreeUtil.findChildrenOfType(file, CupNamedTerminal::class.java).toSet()
        val productions = PsiTreeUtil.findChildrenOfType(file, CupProductionImpl::class.java)

        return grammar {
            nonTerminals.forEach {
                nonTerminal(it)
            }

            terminals.forEach {
                terminal(it)
            }

            for (production in productions) {
                val left = when (val resolve = production.symbol.reference?.resolve()) {
                    is CupProductionImpl -> {
                        nonTerminal(resolve.symbol as SymbolDelegate)
                        resolve.symbol as SymbolDelegate
                    }
                    else -> resolve as SymbolDelegate
                }

                val rightHandSides = mutableSetOf<List<SymbolDelegate>>()
                for (rightHandSide in production.rightHandSideList) {
                    val symbols = mutableListOf<SymbolDelegate>()
                    for (symbol in rightHandSide.symbolList) {
                        val delegate = symbol.reference?.resolve() as? SymbolDelegate ?: (symbol as SymbolDelegate).also { undefinedSymbol(symbol) }
                        symbols.add(delegate)
                    }
                    rightHandSides.add(symbols)
                }

                production {
                    forSymbol(left)
                    rightHandSides.forEach {
                        symbolsFromDelegates(it)
                    }
                }
            }

            val start = FirstProductionAnalyser(file).getProduction()?.getLeft() ?: return@grammar
            startWith(start)
        }
    }

    private fun analyze(file: CupFile): AnalysisResult {
        val holder = AnalysisHolder()

        fileProductions.clear()
        fileProductions.addAll(PsiTreeUtil.findChildrenOfType(file, CupProductionImpl::class.java))

        val start = FirstProductionAnalyser(file).getProduction() ?: return holder.results
        val startingNonTerminal = start.identifyingElement?.reference?.resolve() ?: return holder.results

        visit(startingNonTerminal, holder)

        return holder.results
    }

    private fun visit(symbol: PsiElement, holder: AnalysisHolder, fromStartingProduction: Boolean = true) {
        val rules = fileProductions
            .filter { it.name == symbol.text }
            .flatMap { it.rightHandSideList }
            .map { it as CupRightHandSideImpl }

        for (rule in rules) {
            visit(rule, holder, fromStartingProduction)
        }
    }

    private fun visit(rule: CupRightHandSideImpl, holder: AnalysisHolder, fromStartingProduction: Boolean = true) {
        val symbols = rule.symbolList.mapNotNull { (it as CupSymbolImpl).reference.resolve() }
            .filter { visitedNames.add(it.text) }

        for (symbol in symbols) {
            visit(symbol, holder, fromStartingProduction)
        }
    }
}

data class AnalysisResult(
    val reachedSymbols: Set<PsiElement>,
    val usedSymbols: Set<PsiElement>,
)

class AnalysisHolder {
    private val reachedSymbols = mutableSetOf<PsiElement>()
    private val usedSymbols = mutableSetOf<PsiElement>()

    val results
        get() = AnalysisResult(
            reachedSymbols.toSet(),
            usedSymbols.toSet(),
        )
}
