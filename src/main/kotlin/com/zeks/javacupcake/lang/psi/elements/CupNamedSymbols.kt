package com.zeks.javacupcake.lang.psi.elements

import com.intellij.extapi.psi.StubBasedPsiElementBase
import com.intellij.icons.AllIcons
import com.intellij.lang.ASTNode
import com.intellij.navigation.ItemPresentation
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNamedElement
import com.intellij.psi.stubs.IStubElementType
import com.zeks.javacupcake.analysis.Symbol
import com.zeks.javacupcake.lang.psi.factory.CupElementFactory
import com.zeks.javacupcake.lang.psi.stubs.CupNamedNonTerminalStub
import com.zeks.javacupcake.lang.psi.stubs.CupNamedTerminalStub
import javax.swing.Icon

interface CupNamedSymbol :  PsiNamedElement {
    var symbol: Symbol?
}

abstract class CupNamedTerminal : StubBasedPsiElementBase<CupNamedTerminalStub>, CupNamedSymbol {

    constructor(stub: CupNamedTerminalStub, type: IStubElementType<*, *>) : super(stub, type)
    constructor(node: ASTNode) : super(node)

    override var symbol: Symbol? = null

    override fun getName(): String = stub?.name ?: firstChild.text

    override fun setName(newName: String): PsiElement? =
        replace(CupElementFactory.createDeclaredTerminal(project, newName))

    override fun getPresentation(): ItemPresentation = object : ItemPresentation {
        override fun getPresentableText(): String = name
        override fun getIcon(unused: Boolean): Icon = AllIcons.Nodes.Field
    }
}

abstract class CupNamedNonTerminal : StubBasedPsiElementBase<CupNamedNonTerminalStub>, CupNamedSymbol {

    constructor(stub: CupNamedNonTerminalStub, type: IStubElementType<*, *>) : super(stub, type)
    constructor(node: ASTNode) : super(node)

    override var symbol: Symbol? = null

    override fun getName(): String = stub?.name ?: firstChild.text

    override fun setName(newName: String): PsiElement? =
        replace(CupElementFactory.createDeclaredNonTerminal(project, newName))

    override fun getPresentation(): ItemPresentation = object : ItemPresentation {
        override fun getPresentableText(): String = name
        override fun getIcon(unused: Boolean): Icon = AllIcons.Nodes.AbstractMethod
    }
}
