package com.zeks.javacupcake.inspection.symbols

import com.intellij.codeHighlighting.HighlightDisplayLevel
import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.PsiFile
import com.intellij.psi.util.PsiTreeUtil
import com.zeks.javacupcake.bundle.CupBundle
import com.zeks.javacupcake.lang.psi.CupProduction
import com.zeks.javacupcake.lang.psi.CupVisitor

class CupDuplicateProductionInspection : CupSymbolsInspection() {
    override fun isEnabledByDefault() = true

    override fun getDisplayName() = CupBundle.message("cup.inspection.duplicate_production.display.name")

    override fun getDefaultLevel() = HighlightDisplayLevel.ERROR

    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor =
        CupDuplicateProductionInspectionVisitor(holder)
}

private class CupDuplicateProductionInspectionVisitor(private val holder: ProblemsHolder) : CupVisitor() {
    override fun visitFile(file: PsiFile) {
        val productions = PsiTreeUtil.findChildrenOfType(file, CupProduction::class.java)
            .map { it.firstChild }
        val elementsMap = hashMapOf<String, MutableList<PsiElement>>()

        for (element in productions) {
            elementsMap.getOrPut(element.text) { mutableListOf() }.add(element)
        }

        for (sameElements in elementsMap) {
            if (sameElements.value.size == 1) continue
            val arguments = makeArguments(sameElements.value)
                .filterNotNull()
                .toTypedArray()
            for (element in sameElements.value) {
                val message = CupBundle.message("cup.inspection.duplicate_production.description", *arguments)
                holder.registerProblem(
                    element,
                    message,
                    ProblemHighlightType.GENERIC_ERROR,
                    element.textRangeInParent
                )
            }
        }
    }

    fun makeArguments(elements: List<PsiElement>) = listOf(elements.first().text)
}
