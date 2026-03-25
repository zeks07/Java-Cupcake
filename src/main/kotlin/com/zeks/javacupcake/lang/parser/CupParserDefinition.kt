package com.zeks.javacupcake.lang.parser

import com.intellij.lang.ASTNode
import com.intellij.lang.ParserDefinition
import com.intellij.openapi.project.Project
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.tree.IFileElementType
import com.intellij.psi.tree.TokenSet
import com.zeks.javacupcake.lang.CupLanguage
import com.zeks.javacupcake.lang.file.CupFile
import com.zeks.javacupcake.lang.psi.util.CupTokenSets
import com.zeks.javacupcake.lang.psi.CupTypes
import com.zeks.javacupcake.lang.psi.stubs.CupFileStubElementType

class CupParserDefinition : ParserDefinition {

    override fun createLexer(project: Project) = CupLexerAdapter()

    override fun getCommentTokens() = CupTokenSets.COMMENTS

    override fun getStringLiteralElements(): TokenSet = TokenSet.EMPTY

    override fun createParser(project: Project) = CupParser()

    override fun getFileNodeType(): IFileElementType = CupFileStubElementType

    override fun createFile(viewProvider: FileViewProvider) = CupFile(viewProvider)

    override fun createElement(node: ASTNode): PsiElement = CupTypes.Factory.createElement(node)

}
