package com.zeks.javacupcake.inspection

import com.intellij.codeHighlighting.HighlightDisplayLevel
import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.PsiFile
import com.intellij.psi.util.PsiTreeUtil
import com.zeks.javacupcake.bundle.CupBundle
import com.zeks.javacupcake.lang.psi.CupClassName
import com.zeks.javacupcake.lang.psi.CupProduction
import com.zeks.javacupcake.lang.psi.CupRightHandSide
import com.zeks.javacupcake.lang.psi.CupTypes

class CupClassNameInspection : CupInspectionTool() {
    override fun isEnabledByDefault() = true

    override fun getDefaultLevel() = HighlightDisplayLevel.WARNING

    override fun getGroupPath() = arrayOf(groupDisplayName)

    override fun getDisplayName() = CupBundle.message("cup.inspection.class_name.display.name")

    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor =
        ClassNameInspectionVisitor(holder)
}

private class ClassNameInspectionVisitor(private val holder: ProblemsHolder) : CupDuplicateElementInspection(holder, "class_name") {
    override fun visitFile(file: PsiFile) {
        val classNames = PsiTreeUtil.findChildrenOfType(file, CupClassName::class.java)
        inspectElements(classNames)
    }

    override fun makeArguments(elements: List<PsiElement>) = listOf(elements.first().text)

    override fun getProblemHighlightType() = ProblemHighlightType.WARNING

    override fun visitProduction(production: CupProduction) {
        val rules = PsiTreeUtil.findChildrenOfType(production, CupRightHandSide::class.java)
            .takeIf { it.size > 1 } ?: return
        val classNames = rules.mapNotNull { it.node.findChildByType(CupTypes.CLASS_NAME)?.psi as? CupClassName }

        val productionName = production.firstChild.text

        for (className in classNames) {
            if (className.text != productionName) continue
            holder.registerProblem(
                className,
                CupBundle.message("cup.inspection.class_name_conflict.description", productionName),
                ProblemHighlightType.WARNING
            )
        }
    }
}
