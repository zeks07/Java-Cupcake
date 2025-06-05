package com.zeks.javacupcake.completion

import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.zeks.javacupcake.lang.CupDictionary

class CupLineCompletion : CupCompletion {

    override fun addCompletions(parameters: CompletionParameters, result: CompletionResultSet) {
        for (keyword in CupDictionary.LINE_START_KEYWORDS) {
            result.addElement(
                LookupElementBuilder.create(keyword)
                    .bold()
            )
        }
    }

}
