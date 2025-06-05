package com.zeks.javacupcake.analysis

import com.intellij.psi.PsiElement
import com.intellij.psi.util.CachedValueProvider
import com.intellij.psi.util.CachedValuesManager
import com.intellij.psi.util.PsiModificationTracker
import com.intellij.psi.util.PsiTreeUtil
import com.zeks.javacupcake.file.CupFile
import com.zeks.javacupcake.lang.psi.impl.CupProductionImpl
import com.zeks.javacupcake.lang.psi.impl.CupRightHandSideImpl
import com.zeks.javacupcake.lang.psi.impl.CupSymbolImpl

object FileAnalyser {

    private val fileProductions = mutableListOf<CupProductionImpl>()
    private val visitedNames = mutableSetOf<String>()

    private val analysisProviders = listOf<Analyser>(
        ReachedSymbolsAnalyzer(),
    )

    private fun getResults(file: CupFile): AnalysisResult = CachedValuesManager.getCachedValue(file) {
        val result = analyze(file)
        CachedValueProvider.Result.create(result, PsiModificationTracker.MODIFICATION_COUNT)
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
        for (analyser in analysisProviders) {
            analyser.visit(symbol, holder, fromStartingProduction)
        }

        val rules = fileProductions
            .filter { it.name == symbol.text }
            .flatMap { it.rightHandSideList }
            .map { it as CupRightHandSideImpl }

        for (rule in rules) {
            visit(rule, holder, fromStartingProduction)
        }
    }

    private fun visit(rule: CupRightHandSideImpl, holder: AnalysisHolder, fromStartingProduction: Boolean = true) {
        for (analyser in analysisProviders) {
            analyser.visit(rule, holder, fromStartingProduction)
        }

        val symbols = rule.symbolList.mapNotNull { (it as CupSymbolImpl).reference.resolve() }
            .filter { visitedNames.add(it.text) }

        for (symbol in symbols) {
            visit(symbol, holder, fromStartingProduction)
        }
    }

    data class AnalysisResult(
        private val reachedSymbols: Set<PsiElement>,
        private val usedSymbols: Set<PsiElement>,
    )
}

class AnalysisHolder {
    val reachedSymbols = mutableSetOf<PsiElement>()
    val usedSymbols = mutableSetOf<PsiElement>()

    val results get() = FileAnalyser.AnalysisResult(
        reachedSymbols.toSet(),
        usedSymbols.toSet(),
    )
}
