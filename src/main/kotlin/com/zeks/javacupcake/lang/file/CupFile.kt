package com.zeks.javacupcake.lang.file

import com.intellij.extapi.psi.PsiFileBase
import com.intellij.psi.FileViewProvider
import com.zeks.javacupcake.lang.CupLanguage

class CupFile(viewProvider: FileViewProvider) : PsiFileBase(viewProvider, CupLanguage) {

    override fun getFileType() = CupFileType

    override fun toString() = "Cup File"

}
