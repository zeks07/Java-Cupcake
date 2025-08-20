package com.zeks.javacupcake.completion

import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionProvider
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.icons.AllIcons
import com.intellij.util.ProcessingContext

class CupNewLineCompletionProvider : CompletionProvider<CompletionParameters>() {

    override fun addCompletions(
        parameters: CompletionParameters,
        processingContext: ProcessingContext,
        result: CompletionResultSet
    ) {
        CupLineCompletion().addCompletions(parameters, result)
        result.addElement(
            LookupElementBuilder.create("action code {::}")
                .withIcon(AllIcons.Nodes.Template)
                .withPresentableText("ac")
                .withInsertHandler(CupInsertHandler())
        )
        CupSymbolCompletion().addNonTerminals(parameters, result)
    }

}
