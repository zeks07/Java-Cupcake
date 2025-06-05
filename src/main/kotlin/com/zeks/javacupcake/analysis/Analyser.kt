package com.zeks.javacupcake.analysis

import com.intellij.psi.PsiElement
import com.zeks.javacupcake.lang.psi.impl.CupRightHandSideImpl

interface Analyser {
    fun visit(
        symbol: PsiElement,
        holder: AnalysisHolder,
        fromStartingProduction: Boolean = true,
    )

    fun visit(
        rule: CupRightHandSideImpl,
        holder: AnalysisHolder,
        fromStartingProduction: Boolean = true,
    )
}
