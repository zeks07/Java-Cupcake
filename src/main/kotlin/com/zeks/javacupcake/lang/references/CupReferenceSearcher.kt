package com.zeks.javacupcake.lang.references

import com.intellij.psi.search.PsiSearchHelper
import com.intellij.psi.search.SearchScope
import com.intellij.psi.search.UsageSearchContext
import com.intellij.psi.search.searches.ReferencesSearch
import com.intellij.util.Processor
import com.intellij.util.QueryExecutor
import com.zeks.javacupcake.lang.psi.CupNamedNonTerminal
import com.zeks.javacupcake.lang.psi.CupProductionLine
import com.zeks.javacupcake.lang.psi.CupSymbolElement

class CupReferenceSearcher : QueryExecutor<CupSymbolReference, ReferencesSearch.SearchParameters> {
    override fun execute(parameters: ReferencesSearch.SearchParameters, consumer: Processor<in CupSymbolReference>): Boolean {
        val element = parameters.elementToSearch
        val scope = parameters.scopeDeterminedByUser

        return when (element) {
            is CupNamedNonTerminal -> {
                searchNonTerminalUsages(element, scope, consumer)
            }
            is CupProductionLine -> {
                searchProductionUsages(element, scope, consumer)
            }
            else -> true
        }
    }

    private fun searchNonTerminalUsages(
        element: CupNamedNonTerminal,
        scope: SearchScope,
        consumer: Processor<in CupSymbolReference>
    ) = PsiSearchHelper.getInstance(element.project).processElementsWithWord(
        {
            foundElement, _ ->
                if (foundElement is CupSymbolElement) {
                    val reference = foundElement.reference
                    if (reference.isReferenceTo(element)) {
                        consumer.process(reference)
                    }
                }
                true
        },
        scope,
        element.name,
        UsageSearchContext.IN_CODE,
        true
    )

    private fun searchProductionUsages(
        element: CupProductionLine,
        scope: SearchScope,
        consumer: Processor<in CupSymbolReference>
    ) = PsiSearchHelper.getInstance(element.project).processElementsWithWord(
        {
            foundElement, _ ->
                if (foundElement is CupProductionLine && foundElement.name == element.name) {
                    val nonTerminal = foundElement.firstChild as? CupSymbolElement ?: return@processElementsWithWord true
                    val reference = nonTerminal.reference
                    if (reference.isReferenceTo(element)) {
                        consumer.process(reference)
                    }
                }
                true
        },
        scope,
        element.name,
        UsageSearchContext.IN_CODE,
        true
    )
}