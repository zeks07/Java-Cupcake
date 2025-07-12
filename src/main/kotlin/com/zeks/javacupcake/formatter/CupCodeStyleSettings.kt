package com.zeks.javacupcake.formatter

import com.intellij.psi.codeStyle.CodeStyleSettings
import com.intellij.psi.codeStyle.CustomCodeStyleSettings

@Suppress("PropertyName")
class CupCodeStyleSettings(
    container: CodeStyleSettings,
) : CustomCodeStyleSettings("CupCodeStyleSettings", container) {

    // Indent
    val INDENT_SIZE = 4

    // Lines
    val MAX_BLANK_SPACES_BETWEEN_LINES = 2

    // Code parts
    val SPACE_BEFORE_LEFT_STRING_CODE_BRACES = true
    val LEFT_STRING_CODE_BRACES_ON_NEXT_LINE = false
    val SPACE_AFTER_LEFT_STRING_CODE_BRACES = true
    val SPACE_BEFORE_RIGHT_STRING_CODE_BRACES = true

    // Terminal declaration
    val FORCE_TERMINAL_DECLARATION_IN_SEPARATE_LINE = true
    val TYPE_NAME_IN_SAME_LINE_IN_TERMINAL_DECLARATION = true

    val SPACES_BEFORE_COMMA_IN_TERMINAL_DECLARATION = 0
    val SPACES_AFTER_COMMA_IN_TERMINAL_DECLARATION = 1
    val MAX_BLANK_LINES_BETWEEN_TERMINALS = 1

    val SPACE_BEFORE_SEMICOLON_IN_TERMINAL_DECLARATION = false
    val SEMICOLON_IN_NEW_LINE_IN_TERMINAL_DECLARATION = false

    // Non-terminal declaration
    val FORCE_NON_TERMINAL_DECLARATION_IN_SEPARATE_LINE = true
    val TYPE_NAME_IN_SAME_LINE_IN_NON_TERMINAL_DECLARATION = true

    val SPACES_BEFORE_COMMA_IN_NON_TERMINAL_DECLARATION = 0
    val SPACES_AFTER_COMMA_IN_NON_TERMINAL_DECLARATION = 1
    val MAX_BLANK_LINES_BETWEEN_NON_TERMINALS = 1

    val SPACE_BEFORE_SEMICOLON_IN_NON_TERMINAL_DECLARATION = false
    val SEMICOLON_IN_NEW_LINE_IN_NON_TERMINAL_DECLARATION = false

    // Productions
    val SPACE_BEFORE_ASSIGNMENT_OPERATORS = true
    val SPACE_AFTER_ASSIGNMENT_OPERATORS = true

    val ALIGN_RIGHT_HAND_SIDE_RULES = false

    val ASSIGNMENT_OPERATORS_IN_ONE_LINE = true
    val RULE_DECLARATION_IN_SEPARATE_LINE = true

    val MAX_BLANK_LINES_INSIDE_RIGHT_HAND_SIDE = 2

    val SPACE_BEFORE_BAR = true
    val SPACE_AFTER_BAR = true
    val NEW_LINE_BEFORE_BAR = false
    val NEW_LINE_AFTER_BAR = true

    val SPACE_BEFORE_SEMICOLON_IN_PRODUCTION = true
    val SEMICOLON_IN_NEW_LINE_IN_PRODUCTION = false
}