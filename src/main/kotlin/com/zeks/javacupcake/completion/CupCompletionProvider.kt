package com.zeks.javacupcake.completion

import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionProvider
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.util.ProcessingContext

class CupCompletionProvider : CompletionProvider<CompletionParameters>() {

    override fun addCompletions(
        parameters: CompletionParameters,
        processingContext: ProcessingContext,
        result: CompletionResultSet
    ) {
        CupLineCompletion().addCompletions(parameters, result)
    }

}