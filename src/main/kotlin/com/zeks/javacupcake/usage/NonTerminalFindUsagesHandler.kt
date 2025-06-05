package com.zeks.javacupcake.usage

import com.intellij.find.findUsages.FindUsagesHandler
import com.intellij.find.findUsages.FindUsagesOptions
import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.application.ApplicationManager
import com.intellij.psi.PsiElement
import com.intellij.psi.search.LocalSearchScope
import com.intellij.psi.search.searches.ReferencesSearch
import com.intellij.usageView.UsageInfo
import com.intellij.util.Processor
import com.zeks.javacupcake.lang.psi.CupNamedNonTerminal

class NonTerminalFindUsagesHandler(private val element: CupNamedNonTerminal) : FindUsagesHandler(element) {
    override fun getFindUsagesOptions(context: DataContext?) =
        FindUsagesOptions(element.project).apply {
            isSearchForTextOccurrences = false
            isUsages = true
            searchScope = LocalSearchScope(element.containingFile)
        }

    override fun processElementUsages(
        element: PsiElement,
        processor: Processor<in UsageInfo>,
        options: FindUsagesOptions
    ): Boolean {
        return ApplicationManager.getApplication().runReadAction<Boolean> {
            val references = ReferencesSearch.search(element, options.searchScope).findAll()
            references.all { processor.process(UsageInfo(it)) }
        }
    }

    override fun getSecondaryElements() = emptyArray<PsiElement>()

}