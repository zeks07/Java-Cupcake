package com.zeks.javacupcake.codeInsight.inspection

import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.PsiFile
import com.intellij.psi.util.PsiTreeUtil
import com.zeks.javacupcake.CupBundle
import com.zeks.javacupcake.lang.psi.CupClassName
import com.zeks.javacupcake.lang.psi.CupProduction
import com.zeks.javacupcake.lang.psi.CupTypes
import com.zeks.javacupcake.lang.psi.CupVisitor
import com.zeks.javacupcake.lang.psi.elements.CupRule
import com.zeks.javacupcake.lang.psi.impl.CupProductionImpl
import java.util.Locale.getDefault

class CupClassNameInspection : LocalInspectionTool() {
    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor = object : CupVisitor() {
        override fun visitFile(file: PsiFile) {
            val classNames = PsiTreeUtil.findChildrenOfType(file, CupClassName::class.java)
            DuplicateClassNameVisitor.inspect(classNames, holder)

            val productions = PsiTreeUtil.findChildrenOfType(file, CupProductionImpl::class.java)
            val productionNames = productions.map { it.presentation.presentableText?.capitalize() }

            for (className in classNames) {
                if (className.text !in productionNames) continue
                if (className.text == className.parent.parent.firstChild.text.capitalize()) continue
                holder.registerProblem(
                    className,
                    CupBundle.message("inspection.class_name_extern_conflict.description", className.text),
                    ProblemHighlightType.WARNING
                )
            }
        }

        override fun visitProduction(production: CupProduction) {
            val rules = PsiTreeUtil.findChildrenOfType(production, CupRule::class.java)
                .takeIf { it.size > 1 } ?: return
            val classNames = rules.mapNotNull { it.node.findChildByType(CupTypes.CLASS_NAME)?.psi as? CupClassName }

            val productionName = production.firstChild.text.capitalize()

            for (className in classNames) {
                if (className.text != productionName) continue
                holder.registerProblem(
                    className,
                    CupBundle.message("inspection.class_name_conflict.description", productionName),
                    ProblemHighlightType.WARNING
                )
            }
        }
    }

    object DuplicateClassNameVisitor : CupDuplicateElementVisitor("class_name") {
        fun inspect(classNames: Collection<CupClassName>, holder: ProblemsHolder) =
            inspectElements(holder, classNames)

        override fun makeArguments(elements: List<PsiElement>) = listOf(elements.first().text)

        override fun getProblemHighlightType() = ProblemHighlightType.WARNING
    }

    private fun String.capitalize() = replaceFirstChar { if (it.isLowerCase()) it.titlecase(getDefault()) else it.toString() }
}