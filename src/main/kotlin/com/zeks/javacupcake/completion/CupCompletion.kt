package com.zeks.javacupcake.completion

import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionResultSet

interface CupCompletion {
    fun addCompletions(parameters: CompletionParameters, result: CompletionResultSet)
}