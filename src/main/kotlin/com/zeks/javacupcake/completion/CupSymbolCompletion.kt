package com.zeks.javacupcake.completion

import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.codeInsight.completion.impl.CamelHumpMatcher
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.icons.AllIcons
import com.intellij.psi.util.PsiTreeUtil
import com.zeks.javacupcake.lang.psi.CupDeclaredNonTerminal
import com.zeks.javacupcake.lang.psi.CupDeclaredTerminal

class CupSymbolCompletion {
    fun addAll(
        parameters: CompletionParameters,
        result: CompletionResultSet
    ) {
        addTerminals(parameters, result)
        addNonTerminals(parameters, result)
    }

    fun addNonTerminals(
        parameters: CompletionParameters,
        result: CompletionResultSet
    ) {
        val file = parameters.originalFile

        val nonTerminals = PsiTreeUtil.findChildrenOfType(file, CupDeclaredNonTerminal::class.java)

        val relaxedResult = result.withPrefixMatcher(
            CamelHumpMatcher(result.prefixMatcher.prefix, false)
        )

        for (nonTerminal in nonTerminals) {
            relaxedResult.addElement(
                LookupElementBuilder
                    .create(nonTerminal.text)
                    .withIcon(AllIcons.Nodes.Method)
                    .withPresentableText(nonTerminal.text)
                    .withTypeText("Non-Terminal")
            )
        }
    }

    private fun addTerminals(
        parameters: CompletionParameters,
        result: CompletionResultSet
    ) {
        val file = parameters.originalFile

        val terminals = PsiTreeUtil.findChildrenOfType(file, CupDeclaredTerminal::class.java)

        val relaxedResult = result.withPrefixMatcher(
            CamelHumpMatcher(result.prefixMatcher.prefix, false)
        )

        for (terminal in terminals) {
            relaxedResult.addElement(
                LookupElementBuilder
                    .create(terminal.text)
                    .withIcon(AllIcons.Nodes.Field)
                    .withPresentableText(terminal.text)
                    .withTypeText("Terminal")
            )
        }
    }
}