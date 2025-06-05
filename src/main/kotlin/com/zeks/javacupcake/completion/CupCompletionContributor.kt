package com.zeks.javacupcake.completion

import com.intellij.codeInsight.completion.CompletionContributor
import com.intellij.codeInsight.completion.CompletionType
import com.intellij.patterns.PlatformPatterns
import com.zeks.javacupcake.lang.CupLanguage

class CupCompletionContributor : CompletionContributor() {
    init {
        extend(CompletionType.BASIC,
            PlatformPatterns.psiElement().withLanguage(CupLanguage),
            CupCompletionProvider())
    }
}