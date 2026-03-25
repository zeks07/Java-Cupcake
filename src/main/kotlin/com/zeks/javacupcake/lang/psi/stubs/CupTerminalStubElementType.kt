package com.zeks.javacupcake.lang.psi.stubs

import com.intellij.psi.PsiElement
import com.intellij.psi.stubs.IStubElementType
import com.intellij.psi.stubs.IndexSink
import com.intellij.psi.stubs.StubElement
import com.intellij.psi.stubs.StubInputStream
import com.intellij.psi.stubs.StubOutputStream
import com.zeks.javacupcake.lang.CupLanguage
import com.zeks.javacupcake.lang.psi.elements.CupNamedTerminal
import com.zeks.javacupcake.lang.psi.impl.CupDeclaredTerminalImpl

class CupTerminalStubElementType(debugName: String)
    : IStubElementType<CupNamedTerminalStub, CupNamedTerminal>(debugName, CupLanguage) {

    companion object {
        @JvmStatic
        fun factory(name: String) = CupTerminalStubElementType(name)
    }

    override fun getExternalId() = "cupcake.declaredTerminal"

    override fun createStub(psi: CupNamedTerminal, parentStub: StubElement<out PsiElement?>?): CupNamedTerminalStub =
        CupNamedTerminalStubImpl(parentStub, this, psi.name)

    override fun createPsi(stub: CupNamedTerminalStub): CupNamedTerminal =
        CupDeclaredTerminalImpl(stub, this)

    override fun serialize(stub: CupNamedTerminalStub, dataStream: StubOutputStream) =
        dataStream.writeName(stub.name)

    override fun deserialize(dataStream: StubInputStream, parentStub: StubElement<*>?): CupNamedTerminalStub =
        CupNamedTerminalStubImpl(parentStub, this, dataStream.readName()?.string)

    override fun indexStub(stub: CupNamedTerminalStub, sink: IndexSink) {
        stub.name?.let { sink.occurrence(CupSymbolIndex.KEY, it) }
    }
}