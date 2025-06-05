package com.zeks.javacupcake.analysis

import com.intellij.psi.PsiElement
import com.zeks.javacupcake.lang.psi.impl.CupRightHandSideImpl

class ReachedSymbolsAnalyzer : Analyser {
    override fun visit(
        symbol: PsiElement,
        holder: AnalysisHolder,
        fromStartingProduction: Boolean,
    ) {

    }

    override fun visit(
        rule: CupRightHandSideImpl,
        holder: AnalysisHolder,
        fromStartingProduction: Boolean,
    ) {
        TODO("Not yet implemented")
    }
}