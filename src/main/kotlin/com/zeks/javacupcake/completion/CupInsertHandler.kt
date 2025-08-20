package com.zeks.javacupcake.completion

import com.intellij.codeInsight.completion.InsertHandler
import com.intellij.codeInsight.completion.InsertionContext
import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.openapi.editor.EditorModificationUtil

class CupInsertHandler : InsertHandler<LookupElement> {
    override fun handleInsert(
        context: InsertionContext,
        item: LookupElement
    ) {
        val editor = context.editor
        EditorModificationUtil.moveCaretRelatively(editor, -2)
    }
}