package com.zeks.javacupcake.codeInsight.inspection

import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.zeks.javacupcake.CupBundle
import kotlin.collections.iterator

abstract class CupDuplicateElementVisitor(private val name: String) {
    open fun inspect(file: PsiFile, holder: ProblemsHolder) {}

    protected fun inspectElements(holder: ProblemsHolder, elements: Collection<PsiElement>) {
        val elementsMap = hashMapOf<String, MutableList<PsiElement>>()

        for (element in elements) {
            elementsMap.getOrPut(element.text) { mutableListOf() }.add(element)
        }

        for (sameElements in elementsMap) {
            if (sameElements.value.size == 1) continue
            val arguments = makeArguments(sameElements.value)
                .filterNotNull()
                .toTypedArray()
            for (element in sameElements.value) {
                reportProblem(holder, element, *arguments)
            }
        }
    }

    private fun reportProblem(holder: ProblemsHolder, element: PsiElement, vararg arguments: Any) {
        val message = CupBundle.message("inspection.duplicate.${name}.description", *arguments)
        holder.registerProblem(
            element,
            message,
            getProblemHighlightType()
        )
    }

    protected open fun makeArguments(elements: List<PsiElement>): List<Any?> = emptyList()

    protected open fun getProblemHighlightType(): ProblemHighlightType = ProblemHighlightType.GENERIC_ERROR
}