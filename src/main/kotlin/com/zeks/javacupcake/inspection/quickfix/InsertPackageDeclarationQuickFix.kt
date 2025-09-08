package com.zeks.javacupcake.inspection.quickfix

import com.intellij.codeInspection.LocalQuickFix
import com.intellij.codeInspection.ProblemDescriptor
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.command.CommandProcessor
import com.intellij.openapi.editor.ScrollType
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiDocumentManager
import com.intellij.psi.codeStyle.CodeStyleManager
import com.intellij.psi.util.PsiTreeUtil
import com.zeks.javacupcake.lang.file.CupFile
import com.zeks.javacupcake.lang.psi.CupElementFactory
import com.zeks.javacupcake.lang.psi.CupPackageName

class InsertPackageDeclarationQuickFix : LocalQuickFix {
    override fun getName() = "Insert package declaration"

    override fun getFamilyName() = name

    override fun applyFix(project: Project, descriptor: ProblemDescriptor) {
        val file = descriptor.psiElement.containingFile as CupFile
        val editor = FileEditorManager.getInstance(project).selectedTextEditor ?: return
        val document = editor.document
        val psiDocumentManager = PsiDocumentManager.getInstance(project)

        CommandProcessor.getInstance().executeCommand(project, {
            ApplicationManager.getApplication().runWriteAction {
                psiDocumentManager.commitDocument(document)

                val packageDeclaration = CupElementFactory.createPackageDeclaration(project, "dummy")
                val addedElement = file.firstChild?.let { firstChild ->
                    file.addBefore(packageDeclaration, firstChild)
                } ?: file.add(packageDeclaration)

                CodeStyleManager.getInstance(project).reformat(addedElement)
                psiDocumentManager.doPostponedOperationsAndUnblockDocument(document)

                val declarationTextRange = addedElement.textRange
                val packageName = PsiTreeUtil.findChildrenOfType(addedElement, CupPackageName::class.java)
                val nameTextRange = packageName.first().textRange

                document.insertString(declarationTextRange.endOffset, "\n\n")
                document.replaceString(nameTextRange.startOffset, nameTextRange.endOffset, "")

                psiDocumentManager.commitDocument(document)

                editor.caretModel.moveToOffset(nameTextRange.startOffset)
                editor.scrollingModel.scrollToCaret(ScrollType.MAKE_VISIBLE)
            }
        }, "Insert Package Declaration", document)
    }
}