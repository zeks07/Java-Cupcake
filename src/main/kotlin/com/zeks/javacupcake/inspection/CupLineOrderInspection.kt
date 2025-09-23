package com.zeks.javacupcake.inspection

import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.PsiFile
import com.intellij.psi.util.PsiTreeUtil
import com.zeks.javacupcake.CupBundle
import com.zeks.javacupcake.lang.file.LineType
import com.zeks.javacupcake.lang.psi.CupLine
import com.zeks.javacupcake.lang.psi.CupVisitor

class CupLineOrderInspection : LocalInspectionTool() {
    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor = object : CupVisitor() {
        override fun visitFile(file: PsiFile) {
            val lines = PsiTreeUtil.findChildrenOfType(file, CupLine::class.java)
            if (lines.isEmpty()) return

            var lastSeenType = LineType.EMPTY

            for (line in lines) {
                val currentType = line.lineType
                if (lastSeenType > currentType) {
                    holder.registerProblem(
                        line,
                        CupBundle.message("inspection.line_order.description", currentType, lastSeenType),
                        ProblemHighlightType.GENERIC_ERROR
                    )
                } else {
                    lastSeenType = currentType
                }
            }
        }
    }
}