package com.zeks.javacupcake.inspection.quickfix

import com.intellij.codeInspection.LocalQuickFix
import com.intellij.codeInspection.ProblemDescriptor
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiDocumentManager
import com.intellij.psi.util.PsiTreeUtil
import com.zeks.javacupcake.file.CupFileUtil
import com.zeks.javacupcake.lang.psi.CupElementFactory
import com.zeks.javacupcake.lang.psi.CupLine
import com.zeks.javacupcake.lang.psi.CupTypes

class InsertTerminalDeclarationQuickFix(private val name: String) : LocalQuickFix {
    override fun getFamilyName() = "Insert terminal declaration"

    override fun getName() = "Insert declaration for terminal $name"

    override fun applyFix(project: Project, descriptor: ProblemDescriptor) {
        val file = descriptor.psiElement.containingFile
        val editor = FileEditorManager.getInstance(project).selectedTextEditor ?: return
        val document = editor.document
        val psiDocumentManager = PsiDocumentManager.getInstance(project)

        psiDocumentManager.commitDocument(document)

        val symbol = descriptor.psiElement
        val newTerminalDeclaration = CupElementFactory.createTerminalDeclaration(project, symbol.text)

        var nextLine: CupLine? = null

        val lines = PsiTreeUtil.findChildrenOfType(file, CupLine::class.java)
        for (line in lines) {
            val currentType = CupFileUtil.getElementType(line) ?: continue
            val currentTypeIndex = CupFileUtil.expectedOrder.indexOf(currentType)
            if (currentTypeIndex > CupFileUtil.expectedOrder.indexOf(CupTypes.SYMBOL_DECLARATION)) {
                nextLine = line
                break
            }
        }

        val addedElement = file.addBefore(newTerminalDeclaration, nextLine)

        psiDocumentManager.doPostponedOperationsAndUnblockDocument(document)

        document.insertString(addedElement.textRange.endOffset, "\n\n")

        psiDocumentManager.commitDocument(document)

    }
}