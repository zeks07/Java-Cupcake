package com.zeks.javacupcake.highlighting

import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.HighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.editor.markup.TextAttributes
import java.awt.Font

object CupHighlightColors {
    @JvmStatic
    val KEY = TextAttributesKey.createTextAttributesKey("CUP_KEY", DefaultLanguageHighlighterColors.KEYWORD)
    @JvmStatic
    val SEPARATOR = TextAttributesKey.createTextAttributesKey("CUP_SEPARATOR", DefaultLanguageHighlighterColors.OPERATION_SIGN)
    @JvmStatic
    val SEMICOLON = TextAttributesKey.createTextAttributesKey("CUP_SEMICOLON", DefaultLanguageHighlighterColors.SEMICOLON)
    @JvmStatic
    val COMMENT = TextAttributesKey.createTextAttributesKey("CUP_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT)
    @JvmStatic
    val BAD_CHARACTER = TextAttributesKey.createTextAttributesKey("CUP_BAD_CHARACTER", HighlighterColors.BAD_CHARACTER)
    @JvmStatic
    val ERROR_TERMINAL = TextAttributesKey.createTextAttributesKey("CUP_ERROR_TERMINAL", DefaultLanguageHighlighterColors.CONSTANT)
    @JvmStatic
    val CODE_STRING = TextAttributesKey.createTextAttributesKey("CUP_CODE_STRING", DefaultLanguageHighlighterColors.TEMPLATE_LANGUAGE_COLOR)

    @JvmStatic
    val TERMINAL = TextAttributesKey.createTextAttributesKey("CUP_TERMINAL", DefaultLanguageHighlighterColors.STRING)
    @JvmStatic
    val NON_TERMINAL = TextAttributesKey.createTextAttributesKey("CUP_NON_TERMINAL", DefaultLanguageHighlighterColors.INSTANCE_FIELD)

    @JvmStatic
    val FIRST_PRODUCTION = TextAttributes(null, null, null, null, Font.BOLD)
}
