package com.zeks.javacupcake.lang.psi.stubs

import com.intellij.psi.stubs.IStubElementType
import com.intellij.psi.stubs.StubBase
import com.intellij.psi.stubs.StubElement
import com.zeks.javacupcake.lang.psi.base.CupNamedSymbolStub
import com.zeks.javacupcake.lang.psi.elements.CupNamedNonTerminal

interface CupNamedNonTerminalStub : CupNamedSymbolStub<CupNamedNonTerminal>

class CupNamedNonTerminalStubImpl(
    parent: StubElement<*>?,
    elementType: IStubElementType<*, *>,
    override val name: String?
) : StubBase<CupNamedNonTerminal>(parent, elementType), CupNamedNonTerminalStub