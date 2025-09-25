package com.zeks.javacupcake.codeInsight.fixes

import com.intellij.codeInspection.LocalQuickFix
import com.intellij.codeInspection.ProblemDescriptor
import com.intellij.codeInspection.util.IntentionFamilyName
import com.intellij.codeInspection.util.IntentionName
import com.intellij.openapi.project.Project
import com.zeks.javacupcake.CupBundle
import com.zeks.javacupcake.lang.file.CupFile
import com.zeks.javacupcake.lang.psi.CupElementFactory
import com.zeks.javacupcake.lang.psi.CupSymbolDeclarationType

class InsertSymbolDeclarationFix(val symbolName: String, val symbolType: CupSymbolDeclarationType) : LocalQuickFix {
    override fun getFamilyName(): @IntentionFamilyName String = CupBundle.message("fix.insert_symbol_declaration.family")

    override fun getName(): @IntentionName String = CupBundle.message("fix.insert_symbol_declaration", symbolType, symbolName)

    override fun startInWriteAction(): Boolean = true

    override fun applyFix(
        project: Project,
        descriptor: ProblemDescriptor,
    ) {
        val file = descriptor.psiElement.containingFile
        if (file !is CupFile) return

        val newDeclaration = if (symbolType == CupSymbolDeclarationType.NON_TERMINAL) {
            CupElementFactory.createNonTerminalDeclaration(project, symbolName)
        } else {
            CupElementFactory.createTerminalDeclaration(project, symbolName)
        }

        insertLine(file, newDeclaration)
    }
}