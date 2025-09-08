package com.zeks.javacupcake.formatter

import com.intellij.formatting.Alignment
import com.intellij.formatting.FormattingContext
import com.intellij.formatting.FormattingModel
import com.intellij.formatting.FormattingModelBuilder
import com.intellij.formatting.FormattingModelProvider
import com.intellij.formatting.SpacingBuilder
import com.intellij.formatting.Wrap
import com.intellij.formatting.WrapType
import com.intellij.psi.codeStyle.CodeStyleSettings
import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.TokenSet
import com.zeks.javacupcake.lang.CupLanguage
import com.zeks.javacupcake.lang.psi.CupTokenSets
import com.zeks.javacupcake.lang.psi.CupTypes

private fun SpacingBuilder.betweenInside(
    leftSet: TokenSet,
    rightType: IElementType,
    parentType: IElementType
): SpacingBuilder.RuleBuilder =
    betweenInside(leftSet, TokenSet.create(rightType), parentType)


class CupFormattingModelBuilder : FormattingModelBuilder {
    override fun createModel(formattingContext: FormattingContext): FormattingModel {
        val codeStyleSettings = formattingContext.codeStyleSettings

        return FormattingModelProvider.createFormattingModelForPsiFile(
            formattingContext.containingFile,
            CupBlock(
                formattingContext.node,
                Wrap.createWrap(WrapType.NONE, false),
                Alignment.createAlignment(),
                createSpaceBuilder(codeStyleSettings),
                codeStyleSettings
            ),
            codeStyleSettings
        )
    }

    private fun createSpaceBuilder(settings: CodeStyleSettings): SpacingBuilder {
        val customSettings = settings.getCustomSettings(CupCodeStyleSettings::class.java)

        return SpacingBuilder(settings, CupLanguage)
            .between(CupTokenSets.LINE, CupTokenSets.LINE).spacing(
                1,
                1,
                1,
                true,
                customSettings.MAX_BLANK_SPACES_BETWEEN_LINES
            )


            .between(CupTokenSets.CODE_PART_FIRST, CupTokenSets.CODE_PART_SECOND).spacing(1, 1, 0, false, 0)


//            .betweenInside(CupTokenSets.CODE_PART_SECOND, CupTypes.OPEN_CODE_STRING, CupTokenSets.CODE_PARTS).spacing(
//                if (customSettings.SPACE_BEFORE_LEFT_STRING_CODE_BRACES) 1 else 0,
//                if (customSettings.SPACE_BEFORE_LEFT_STRING_CODE_BRACES) 1 else 0,
//                if (customSettings.LEFT_STRING_CODE_BRACES_ON_NEXT_LINE) 1 else 0,
//                customSettings.LEFT_STRING_CODE_BRACES_ON_NEXT_LINE,
//                0
//            )
            .afterInside(CupTypes.OPEN_CODE_STRING, CupTokenSets.CODE_PARTS).spacing(
                if (customSettings.SPACE_AFTER_LEFT_STRING_CODE_BRACES) 1 else 0,
                if (customSettings.SPACE_AFTER_LEFT_STRING_CODE_BRACES) 1 else 0,
                if (customSettings.LEFT_STRING_CODE_BRACES_ON_NEXT_LINE) 1 else 0,
                true,
                1
            )
            .beforeInside(CupTypes.CLOSE_CODE_STRING, CupTokenSets.CODE_PARTS).spacing(
                if (customSettings.SPACE_BEFORE_RIGHT_STRING_CODE_BRACES) 1 else 0,
                if (customSettings.SPACE_BEFORE_RIGHT_STRING_CODE_BRACES) 1 else 0,
                0,
                true,
                1
            )
            .afterInside(CupTypes.CLOSE_CODE_STRING, CupTokenSets.CODE_PARTS).spacing(1, 1, 1, true, 1)


            .betweenInside(CupTypes.TERMINAL_, CupTypes.TYPE_NAME, CupTypes.TERMINAL_DECLARATION).spacing(
                1,
                1,
                if (customSettings.TYPE_NAME_IN_SAME_LINE_IN_TERMINAL_DECLARATION) 0 else 1,
                !customSettings.TYPE_NAME_IN_SAME_LINE_IN_TERMINAL_DECLARATION,
                0
            )
            .betweenInside(CupTypes.TYPE_NAME, CupTypes.DECLARED_TERMINAL, CupTypes.TERMINAL_DECLARATION).spacing(
                1,
                1,
                if (customSettings.FORCE_TERMINAL_DECLARATION_IN_SEPARATE_LINE) 1 else 0,
                true,
                1
            )
            .betweenInside(CupTypes.TERMINAL_, CupTypes.DECLARED_TERMINAL, CupTypes.TERMINAL_DECLARATION).spacing(
                1,
                1,
                if (customSettings.FORCE_TERMINAL_DECLARATION_IN_SEPARATE_LINE) 1 else 0,
                customSettings.FORCE_TERMINAL_DECLARATION_IN_SEPARATE_LINE,
                1
            )

            .beforeInside(CupTypes.COMMA, CupTypes.TERMINAL_DECLARATION)
            .spaces(customSettings.SPACES_BEFORE_COMMA_IN_TERMINAL_DECLARATION)
            .afterInside(CupTypes.COMMA, CupTypes.TERMINAL_DECLARATION).spacing(
                customSettings.SPACES_AFTER_COMMA_IN_TERMINAL_DECLARATION,
                customSettings.SPACES_AFTER_COMMA_IN_TERMINAL_DECLARATION,
                0,
                true,
                customSettings.MAX_BLANK_LINES_BETWEEN_TERMINALS
            )

            .beforeInside(CupTypes.SEMICOLON, CupTypes.TERMINAL_DECLARATION).spacing(
                if (customSettings.SPACE_BEFORE_SEMICOLON_IN_TERMINAL_DECLARATION) 1 else 0,
                if (customSettings.SPACE_BEFORE_SEMICOLON_IN_TERMINAL_DECLARATION) 1 else 0,
                if (customSettings.SEMICOLON_IN_NEW_LINE_IN_TERMINAL_DECLARATION) 1 else 0,
                customSettings.SEMICOLON_IN_NEW_LINE_IN_TERMINAL_DECLARATION,
                if (customSettings.SEMICOLON_IN_NEW_LINE_IN_TERMINAL_DECLARATION) 1 else 0
            )
            .afterInside(CupTypes.SEMICOLON, CupTypes.TERMINAL_DECLARATION).spacing(1, 1, 1, true, 1)


            .between(CupTypes.NON, CupTypes.TERMINAL_).spacing(1, 1, 0, false, 0)

            .betweenInside(CupTypes.TERMINAL_, CupTypes.TYPE_NAME, CupTypes.NON_TERMINAL_DECLARATION).spacing(
                1,
                1,
                if (customSettings.TYPE_NAME_IN_SAME_LINE_IN_NON_TERMINAL_DECLARATION) 0 else 1,
                !customSettings.TYPE_NAME_IN_SAME_LINE_IN_NON_TERMINAL_DECLARATION,
                0
            )
            .betweenInside(CupTypes.TYPE_NAME, CupTypes.DECLARED_NON_TERMINAL, CupTypes.NON_TERMINAL_DECLARATION)
            .spacing(
                1,
                1,
                if (customSettings.FORCE_NON_TERMINAL_DECLARATION_IN_SEPARATE_LINE) 1 else 0,
                true,
                1
            )
            .betweenInside(CupTypes.TERMINAL_, CupTypes.DECLARED_NON_TERMINAL, CupTypes.NON_TERMINAL_DECLARATION)
            .spacing(
                1,
                1,
                if (customSettings.FORCE_NON_TERMINAL_DECLARATION_IN_SEPARATE_LINE) 1 else 0,
                customSettings.FORCE_NON_TERMINAL_DECLARATION_IN_SEPARATE_LINE,
                1
            )

            .beforeInside(CupTypes.COMMA, CupTypes.NON_TERMINAL_DECLARATION)
            .spaces(customSettings.SPACES_BEFORE_COMMA_IN_NON_TERMINAL_DECLARATION)
            .afterInside(CupTypes.COMMA, CupTypes.NON_TERMINAL_DECLARATION).spacing(
                customSettings.SPACES_AFTER_COMMA_IN_NON_TERMINAL_DECLARATION,
                customSettings.SPACES_AFTER_COMMA_IN_NON_TERMINAL_DECLARATION,
                0,
                true,
                customSettings.MAX_BLANK_LINES_BETWEEN_NON_TERMINALS
            )

            .beforeInside(CupTypes.SEMICOLON, CupTypes.NON_TERMINAL_DECLARATION).spacing(
                if (customSettings.SPACE_BEFORE_SEMICOLON_IN_NON_TERMINAL_DECLARATION) 1 else 0,
                if (customSettings.SPACE_BEFORE_SEMICOLON_IN_NON_TERMINAL_DECLARATION) 1 else 0,
                if (customSettings.SEMICOLON_IN_NEW_LINE_IN_NON_TERMINAL_DECLARATION) 1 else 0,
                customSettings.SEMICOLON_IN_NEW_LINE_IN_NON_TERMINAL_DECLARATION,
                if (customSettings.SEMICOLON_IN_NEW_LINE_IN_NON_TERMINAL_DECLARATION) 1 else 0
            )
            .afterInside(CupTypes.SEMICOLON, CupTypes.NON_TERMINAL_DECLARATION).spacing(1, 1, 1, true, 1)


            .betweenInside(CupTypes.SYMBOL, CupTypes.ASSIGN_OPERATOR, CupTypes.PRODUCTION).spacing(
                if (customSettings.SPACE_BEFORE_ASSIGNMENT_OPERATORS) 1 else 0,
                if (customSettings.SPACE_BEFORE_ASSIGNMENT_OPERATORS) 1 else 0,
                if (customSettings.ASSIGNMENT_OPERATORS_IN_ONE_LINE) 0 else 1,
                !customSettings.ASSIGNMENT_OPERATORS_IN_ONE_LINE,
                0
            )
            .afterInside(CupTypes.ASSIGN_OPERATOR, CupTypes.PRODUCTION).spacing(
                if (customSettings.SPACE_AFTER_ASSIGNMENT_OPERATORS) 1 else 0,
                if (customSettings.SPACE_AFTER_ASSIGNMENT_OPERATORS) 1 else 0,
                if (customSettings.RULE_DECLARATION_IN_SEPARATE_LINE) 1 else 0,
                true,
                customSettings.MAX_BLANK_LINES_INSIDE_RIGHT_HAND_SIDE
            )

            .betweenInside(CupTypes.RIGHT_HAND_SIDE, CupTypes.BAR, CupTypes.PRODUCTION).spacing(
                if (customSettings.SPACE_BEFORE_BAR) 1 else 0,
                if (customSettings.SPACE_BEFORE_BAR) 1 else 0,
                if (customSettings.NEW_LINE_BEFORE_BAR) 1 else 0,
                customSettings.NEW_LINE_BEFORE_BAR,
                if (customSettings.NEW_LINE_BEFORE_BAR) customSettings.MAX_BLANK_LINES_INSIDE_RIGHT_HAND_SIDE else 0
            )
            .betweenInside(CupTypes.BAR, CupTypes.RIGHT_HAND_SIDE, CupTypes.PRODUCTION).spacing(
                if (customSettings.SPACE_AFTER_BAR) 1 else 0,
                if (customSettings.SPACE_AFTER_BAR) 1 else 0,
                if (customSettings.NEW_LINE_AFTER_BAR) 1 else 0,
                customSettings.NEW_LINE_AFTER_BAR,
                if (customSettings.NEW_LINE_AFTER_BAR) customSettings.MAX_BLANK_LINES_INSIDE_RIGHT_HAND_SIDE else 0
            )

            .beforeInside(CupTypes.SEMICOLON, CupTypes.PRODUCTION).spacing(
                if (customSettings.SPACE_BEFORE_SEMICOLON_IN_PRODUCTION) 1 else 0,
                if (customSettings.SPACE_BEFORE_SEMICOLON_IN_PRODUCTION) 1 else 0,
                if (customSettings.SEMICOLON_IN_NEW_LINE_IN_PRODUCTION) 1 else 0,
                customSettings.SEMICOLON_IN_NEW_LINE_IN_PRODUCTION,
                if (customSettings.SEMICOLON_IN_NEW_LINE_IN_PRODUCTION) 1 else 0
            )
    }
}