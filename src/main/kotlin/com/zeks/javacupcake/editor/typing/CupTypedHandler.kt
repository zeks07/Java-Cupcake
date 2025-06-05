package com.zeks.javacupcake.editor.typing

import com.intellij.codeInsight.editorActions.TypedHandlerDelegate
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.EditorModificationUtil
import com.intellij.openapi.fileTypes.FileType
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiFile
import com.zeks.javacupcake.lang.CupLanguage

class CupTypedHandler : TypedHandlerDelegate() {
    override fun beforeCharTyped(
        char: Char,
        project: Project,
        editor: Editor,
        file: PsiFile,
        fileType: FileType
    ): Result {
        if (file.language !is CupLanguage) return Result.CONTINUE
        if (char != ':') return Result.CONTINUE

        val offset = editor.caretModel.offset
        if (offset == 0 || !isAfterBrace(editor, offset)) return Result.CONTINUE

        EditorModificationUtil.insertStringAtCaret(editor, "::}")
        editor.caretModel.moveToOffset(offset + 1)
        return Result.STOP
    }

    private fun isAfterBrace(editor: Editor, offset: Int): Boolean {
        return editor.document.getText(TextRange(offset - 1, offset)) == "{"
    }
}