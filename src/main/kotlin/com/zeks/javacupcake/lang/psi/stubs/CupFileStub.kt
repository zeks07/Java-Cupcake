package com.zeks.javacupcake.lang.psi.stubs

import com.intellij.psi.stubs.PsiFileStubImpl
import com.intellij.psi.tree.IStubFileElementType
import com.zeks.javacupcake.lang.CupLanguage
import com.zeks.javacupcake.lang.file.CupFile

class CupFileStub(file: CupFile?) : PsiFileStubImpl<CupFile>(file)

object CupFileStubElementType
    : IStubFileElementType<CupFileStub>("cupcake.file", CupLanguage) {

    override fun getStubVersion(): Int = 1

    override fun getExternalId() = "cupcake.file"
}