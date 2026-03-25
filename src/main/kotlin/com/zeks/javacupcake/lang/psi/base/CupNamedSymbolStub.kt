package com.zeks.javacupcake.lang.psi.base

import com.intellij.psi.stubs.StubElement
import com.zeks.javacupcake.lang.psi.elements.CupNamedSymbol

interface CupNamedSymbolStub<T : CupNamedSymbol> : StubElement<T>{
    val name: String?
}