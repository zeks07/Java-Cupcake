package com.zeks.javacupcake.lang.psi.stubs

import com.intellij.psi.PsiElement
import com.intellij.psi.stubs.IStubElementType
import com.intellij.psi.stubs.IndexSink
import com.intellij.psi.stubs.StubElement
import com.intellij.psi.stubs.StubInputStream
import com.intellij.psi.stubs.StubOutputStream
import com.zeks.javacupcake.lang.psi.elements.CupProductionLine
import com.zeks.javacupcake.lang.psi.impl.CupProductionImpl

class CupProductionStubElementType(debugName: String)
    : IStubElementType<CupProductionStub, CupProductionLine>(debugName, com.zeks.javacupcake.lang.CupLanguage) {

    companion object {
        @JvmStatic
        fun factory(name: String) = CupProductionStubElementType(name)
    }

    override fun getExternalId() = "cupcake.production"

    override fun createStub(psi: CupProductionLine, parentStub: StubElement<out PsiElement?>?): CupProductionStub =
        CupProductionStubImpl(parentStub, this, psi.name)

    override fun createPsi(stub: CupProductionStub): CupProductionLine =
        CupProductionImpl(stub, this)

    override fun serialize(stub: CupProductionStub, dataStream: StubOutputStream) =
        dataStream.writeName(stub.name)

    override fun deserialize(dataStream: StubInputStream, parentStub: StubElement<*>?): CupProductionStub =
        CupProductionStubImpl(parentStub, this, dataStream.readName()?.string)

    override fun indexStub(stub: CupProductionStub, sink: IndexSink) {
        stub.name?.let { sink.occurrence(CupProductionIndex.KEY, it) }
    }
}