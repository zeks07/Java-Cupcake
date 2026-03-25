package com.zeks.javacupcake.lang.psi.stubs

import com.intellij.psi.stubs.IStubElementType
import com.intellij.psi.stubs.StubBase
import com.intellij.psi.stubs.StubElement
import com.zeks.javacupcake.lang.psi.elements.CupProductionLine

interface CupProductionStub : StubElement<CupProductionLine> {
    val name: String?
}

class CupProductionStubImpl(
    parent: StubElement<*>?,
    elementType: IStubElementType<*, *>,
    override val name: String?
) : StubBase<CupProductionLine>(parent, elementType), CupProductionStub