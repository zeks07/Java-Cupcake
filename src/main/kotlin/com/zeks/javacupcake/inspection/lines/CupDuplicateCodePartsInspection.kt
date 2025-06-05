package com.zeks.javacupcake.inspection.lines

import com.intellij.codeHighlighting.HighlightDisplayLevel
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.PsiFile
import com.intellij.psi.util.PsiTreeUtil
import com.zeks.javacupcake.bundle.CupBundle
import com.zeks.javacupcake.inspection.CupDuplicateElementInspection
import com.zeks.javacupcake.inspection.CupInspectionTool
import com.zeks.javacupcake.lang.psi.CupCodeParts

class CupDuplicateCodePartsInspection : CupInspectionTool() {
    override fun isEnabledByDefault() = true

    override fun getDisplayName() = CupBundle.message("cup.inspection.duplicate_code_parts.display.name")

    override fun getDefaultLevel() = HighlightDisplayLevel.ERROR

    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor =
        CupDuplicateCodePartsInspectionVisitor(holder)
}

private class CupDuplicateCodePartsInspectionVisitor(holder: ProblemsHolder) : CupDuplicateElementInspection(holder, "duplicate_code_parts") {
    override fun visitFile(file: PsiFile) {
        val codeParts = PsiTreeUtil.findChildrenOfType(file, CupCodeParts::class.java).stream()
            .map { codeParts -> codeParts.firstChild }
            .toList()
        inspectElements(codeParts)
    }

    override fun makeArguments(elements: List<PsiElement>) = listOf(elements.first().firstChild.text)
}
