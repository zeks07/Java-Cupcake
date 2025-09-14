package com.zeks.javacupcake.highlighting

import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IElementType
import com.zeks.javacupcake.lang.parser.CupLexerAdapter
import com.zeks.javacupcake.lang.psi.CupTokenSets
import com.zeks.javacupcake.lang.psi.CupTypes

class CupSyntaxHighlighter : SyntaxHighlighterBase() {

    override fun getHighlightingLexer() = CupLexerAdapter()

    override fun getTokenHighlights(elementType: IElementType?) =
        when (elementType) {
            in CupTokenSets.KEYWORDS -> KEY_KEYS
            in CupTokenSets.SEPARATORS -> SEPARATOR_KEYS
            in CupTokenSets.COMMENTS -> COMMENT_KEYS
            CupTypes.SEMICOLON -> SEMICOLON_KEYS
            TokenType.BAD_CHARACTER -> BAD_CHAR_KEYS
            else -> EMPTY_KEYS
        }

    private companion object {
        @JvmStatic
        val KEY_KEYS = arrayOf<TextAttributesKey>(CupHighlightColors.KEY)
        @JvmStatic
        val SEPARATOR_KEYS = arrayOf<TextAttributesKey>(CupHighlightColors.SEPARATOR)
        @JvmStatic
        val SEMICOLON_KEYS = arrayOf<TextAttributesKey>(CupHighlightColors.SEMICOLON)
        @JvmStatic
        val BAD_CHAR_KEYS = arrayOf<TextAttributesKey>(CupHighlightColors.BAD_CHARACTER)
        @JvmStatic
        val COMMENT_KEYS = arrayOf<TextAttributesKey>(CupHighlightColors.COMMENT)
        @JvmStatic
        val EMPTY_KEYS = arrayOf<TextAttributesKey>()
    }

}
