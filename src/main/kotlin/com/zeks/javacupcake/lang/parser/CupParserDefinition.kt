package com.zeks.javacupcake.lang.parser

import com.intellij.lang.ASTNode
import com.intellij.lang.ParserDefinition
import com.intellij.openapi.project.Project
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.tree.IFileElementType
import com.intellij.psi.tree.TokenSet
import com.zeks.javacupcake.lang.CupLanguage
import com.zeks.javacupcake.file.CupFile
import com.zeks.javacupcake.lang.psi.CupTokenSets
import com.zeks.javacupcake.lang.psi.CupTypes

val FILE = IFileElementType(CupLanguage)

class CupParserDefinition : ParserDefinition {

    override fun createLexer(project: Project) = CupLexerAdapter()

    override fun getCommentTokens() = CupTokenSets.COMMENTS

    override fun getStringLiteralElements(): TokenSet = TokenSet.EMPTY

    override fun createParser(project: Project) = CupPaser()

    override fun getFileNodeType() = FILE

    override fun createFile(viewProvider: FileViewProvider) = CupFile(viewProvider)

    override fun createElement(node: ASTNode): PsiElement = CupTypes.Factory.createElement(node)

}
