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
import com.zeks.javacupcake.lang.CupLanguage

class CupFormattingModelBuilder : FormattingModelBuilder {
    override fun createModel(formattingContext: FormattingContext): FormattingModel {
        val codeStyleSettings = formattingContext.codeStyleSettings

        return FormattingModelProvider
            .createFormattingModelForPsiFile(
                formattingContext.containingFile,
                CupBlock(
                    formattingContext.node,
                    Wrap.createWrap(WrapType.NONE, false),
                    Alignment.createAlignment(),
                    createSpaceBuilder(codeStyleSettings)
                ),
                codeStyleSettings
            )
    }

    private fun createSpaceBuilder(settings: CodeStyleSettings) =
        SpacingBuilder(settings, CupLanguage)
}