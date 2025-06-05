package com.zeks.javacupcake.lang.psi

import com.intellij.psi.tree.IElementType
import com.zeks.javacupcake.lang.CupLanguage

class CupTokenType(debugName: String) : IElementType(debugName, CupLanguage) {
    override fun toString(): String = super.toString()
}
