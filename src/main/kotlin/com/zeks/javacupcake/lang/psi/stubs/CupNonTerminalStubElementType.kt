package com.zeks.javacupcake.lang.psi.stubs

import com.intellij.psi.PsiElement
import com.intellij.psi.stubs.IStubElementType
import com.intellij.psi.stubs.IndexSink
import com.intellij.psi.stubs.StubElement
import com.intellij.psi.stubs.StubInputStream
import com.intellij.psi.stubs.StubOutputStream
import com.zeks.javacupcake.lang.CupLanguage
import com.zeks.javacupcake.lang.psi.elements.CupNamedNonTerminal
import com.zeks.javacupcake.lang.psi.impl.CupDeclaredNonTerminalImpl

class CupNonTerminalStubElementType(debugName: String)
    : IStubElementType<CupNamedNonTerminalStub, CupNamedNonTerminal>(debugName, CupLanguage) {

    companion object {
        @JvmStatic
        fun factory(name: String) = CupNonTerminalStubElementType(name)
    }

    override fun getExternalId() = "cupcake.declaredNonTerminal"

    override fun createStub(psi: CupNamedNonTerminal, parentStub: StubElement<out PsiElement>?) =
        CupNamedNonTerminalStubImpl(parentStub, this, psi.name)

    override fun createPsi(stub: CupNamedNonTerminalStub): CupNamedNonTerminal =
        CupDeclaredNonTerminalImpl(stub, this)

    override fun serialize(stub: CupNamedNonTerminalStub, dataStream: StubOutputStream) =
        dataStream.writeName(stub.name)

    override fun deserialize(dataStream: StubInputStream, parentStub: StubElement<*>?): CupNamedNonTerminalStub =
        CupNamedNonTerminalStubImpl(parentStub, this, dataStream.readName()?.string)

    override fun indexStub(stub: CupNamedNonTerminalStub, sink: IndexSink) {
        stub.name?.let { sink.occurrence(CupSymbolIndex.KEY, it) }
    }
}