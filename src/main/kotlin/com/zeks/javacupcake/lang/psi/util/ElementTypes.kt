package com.zeks.javacupcake.lang.psi.util

import com.intellij.psi.tree.IElementType
import com.zeks.javacupcake.lang.CupLanguage

class CupElementType(debugName: String) : IElementType(debugName, CupLanguage)

class CupTokenType(debugName: String) : IElementType(debugName, CupLanguage)
