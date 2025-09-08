package com.zeks.javacupcake.lang.file

import com.intellij.openapi.fileTypes.LanguageFileType
import com.zeks.javacupcake.CupIcons
import com.zeks.javacupcake.lang.CupLanguage

object CupFileType : LanguageFileType(CupLanguage) {

    override fun getName() = "Cup File"

    override fun getDescription() = "Cup language file"

    override fun getDefaultExtension() = "cup"

    override fun getIcon() = CupIcons.FILE
}
