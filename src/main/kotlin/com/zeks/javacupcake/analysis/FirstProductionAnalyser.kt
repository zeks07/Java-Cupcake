package com.zeks.javacupcake.analysis

import com.intellij.psi.util.PsiTreeUtil
import com.zeks.javacupcake.file.CupFile
import com.zeks.javacupcake.lang.psi.CupStartSpec
import com.zeks.javacupcake.lang.psi.impl.CupProductionImpl

class FirstProductionAnalyser(private val file: CupFile) {
    fun getProduction() = findInStartDeclaration() ?: findFirstProduction()

    private fun findInStartDeclaration(): CupProductionImpl? {
        val startDeclaration = PsiTreeUtil.findChildrenOfType(file, CupStartSpec::class.java)
            .singleOrNull() ?: return null

        val productionName = startDeclaration.symbol?.text ?: return null

        return PsiTreeUtil.findChildrenOfType(file, CupProductionImpl::class.java)
            .singleOrNull { it.name == productionName }
    }

    private fun findFirstProduction() =
        PsiTreeUtil.findChildrenOfType(file, CupProductionImpl::class.java).firstOrNull()

}