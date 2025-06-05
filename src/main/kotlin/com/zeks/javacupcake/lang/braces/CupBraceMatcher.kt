package com.zeks.javacupcake.lang.braces

import com.intellij.lang.BracePair
import com.intellij.lang.PairedBraceMatcher
import com.intellij.psi.PsiFile
import com.intellij.psi.tree.IElementType
import com.zeks.javacupcake.lang.psi.CupTypes

class CupBraceMatcher : PairedBraceMatcher {
    override fun getPairs() = arrayOf(
        BracePair(CupTypes.OPEN_CODE_STRING, CupTypes.CLOSE_CODE_STRING, false),
        BracePair(CupTypes.LPAREN, CupTypes.RPAREN, false),
    )

    override fun isPairedBracesAllowedBeforeType(
        lbraceType: IElementType,
        contextType: IElementType?
    ) = true

    override fun getCodeConstructStart(file: PsiFile?, openingBraceOffset: Int) = openingBraceOffset
}