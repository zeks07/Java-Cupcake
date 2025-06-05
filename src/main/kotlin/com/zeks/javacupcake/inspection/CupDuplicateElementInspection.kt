package com.zeks.javacupcake.inspection

import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElement
import com.zeks.javacupcake.bundle.CupBundle
import com.zeks.javacupcake.lang.psi.CupVisitor

abstract class CupDuplicateElementInspection(private val holder: ProblemsHolder, private val name: String) : CupVisitor() {
    protected fun inspectElements(elements: Collection<PsiElement>) {
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
                reportProblem(element, *arguments)
            }
        }
    }

    private fun reportProblem(element: PsiElement, vararg arguments: Any) {
        val message = CupBundle.message("cup.inspection.${name}.description", *arguments)
        holder.registerProblem(
            element,
            message,
            getProblemHighlightType()
        )
    }

    protected open fun makeArguments(elements: List<PsiElement>): List<Any?> = emptyList()

    protected open fun getProblemHighlightType(): ProblemHighlightType = ProblemHighlightType.GENERIC_ERROR
}
