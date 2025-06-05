package com.zeks.javacupcake.highlighting

import com.intellij.openapi.fileTypes.SyntaxHighlighterFactory
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile

class CupSyntaxHighlighterFactory() : SyntaxHighlighterFactory() {
    override fun getSyntaxHighlighter(project: Project?, file: VirtualFile?) = CupSyntaxHighlighter()
}