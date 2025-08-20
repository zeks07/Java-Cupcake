package com.zeks.javacupcake.completion

import com.intellij.codeInsight.completion.CompletionContributor
import com.intellij.codeInsight.completion.CompletionType
import com.intellij.patterns.PlatformPatterns
import com.zeks.javacupcake.lang.psi.impl.CupProductionImpl
import com.zeks.javacupcake.lang.psi.impl.CupRightHandSideImpl

class CupCompletionContributor : CompletionContributor() {
    init {

        extend(
            CompletionType.BASIC,
            PlatformPatterns
                .psiElement()
                .withSuperParent(2, CupProductionImpl::class.java),
            CupNewLineCompletionProvider()
        )

        extend(
            CompletionType.BASIC,
            PlatformPatterns
                .psiElement()
                .inside(CupRightHandSideImpl::class.java),
            CupRightHandSideCompletionProvider()
        )

    }
}