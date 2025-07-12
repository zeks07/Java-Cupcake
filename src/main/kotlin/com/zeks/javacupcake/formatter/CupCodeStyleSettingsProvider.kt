package com.zeks.javacupcake.formatter

import com.intellij.application.options.CodeStyleAbstractConfigurable
import com.intellij.application.options.TabbedLanguageCodeStylePanel
import com.intellij.psi.codeStyle.CodeStyleConfigurable
import com.intellij.psi.codeStyle.CodeStyleSettings
import com.intellij.psi.codeStyle.CodeStyleSettingsProvider
import com.intellij.psi.codeStyle.CustomCodeStyleSettings
import com.zeks.javacupcake.lang.CupLanguage

class CupCodeStyleSettingsProvider : CodeStyleSettingsProvider() {
    override fun createCustomSettings(settings: CodeStyleSettings): CustomCodeStyleSettings = CupCodeStyleSettings(settings)

    override fun getConfigurableDisplayName() = CupLanguage.displayName

    override fun createConfigurable(
        settings: CodeStyleSettings,
        modelSettings: CodeStyleSettings
    ): CodeStyleConfigurable = object : CodeStyleAbstractConfigurable(settings, modelSettings, configurableDisplayName) {
        override fun createPanel(settings: CodeStyleSettings) = CupCodeStyleMainPanel(currentSettings, settings)
    }

    private class CupCodeStyleMainPanel(currentSettings: CodeStyleSettings, settings: CodeStyleSettings) : TabbedLanguageCodeStylePanel(CupLanguage, currentSettings, settings)
}