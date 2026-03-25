package com.zeks.javacupcake.lang.psi.elements

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.extapi.psi.StubBasedPsiElementBase
import com.intellij.icons.AllIcons
import com.intellij.lang.ASTNode
import com.intellij.navigation.ItemPresentation
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNameIdentifierOwner
import com.intellij.psi.stubs.IStubElementType
import com.zeks.javacupcake.lang.file.LineType
import com.zeks.javacupcake.lang.psi.base.CupLine
import com.zeks.javacupcake.lang.psi.base.CupSymbolDeclaration
import com.zeks.javacupcake.lang.psi.base.CupSymbolDeclarationType
import com.zeks.javacupcake.lang.psi.factory.CupElementFactory
import com.zeks.javacupcake.lang.psi.impl.CupSymbolImpl
import com.zeks.javacupcake.lang.psi.stubs.CupProductionStub
import java.util.Locale
import java.util.Locale.getDefault
import javax.swing.Icon

abstract class CupPackageSpecLine(node: ASTNode) : ASTWrapperPsiElement(node), CupLine {
    override val lineType
        get() = LineType.PACKAGE
}

abstract class CupImportStatementLine(node: ASTNode) : ASTWrapperPsiElement(node), CupLine {
    override val lineType
        get() = LineType.IMPORT
}

abstract class CupStartLine(node: ASTNode) : ASTWrapperPsiElement(node), CupLine {
    override val lineType
        get() = LineType.START_SPEC
}

abstract class CupPrecedenceDeclarationLine(node: ASTNode) : ASTWrapperPsiElement(node), CupLine {
    override val lineType
        get() = LineType.PRECEDENCE_DECLARATION
}

abstract class CupTerminalDeclarationLine(node: ASTNode) : ASTWrapperPsiElement(node), CupSymbolDeclaration {
    override val symbolDeclarationType: CupSymbolDeclarationType
        get() = CupSymbolDeclarationType.TERMINAL
}

abstract class CupNonTerminalDeclarationLine(node: ASTNode) : ASTWrapperPsiElement(node), CupSymbolDeclaration {
    override val symbolDeclarationType: CupSymbolDeclarationType
        get() = CupSymbolDeclarationType.NON_TERMINAL
}

abstract class CupProductionLine
    : StubBasedPsiElementBase<CupProductionStub>, PsiNameIdentifierOwner, CupLine {

    constructor(stub: CupProductionStub, type: IStubElementType<*, *>) : super(stub, type)
    constructor(node: ASTNode) : super(node)

    override val lineType
        get() = LineType.PRODUCTION

    override fun getName(): String = stub?.name ?: firstChild.text

    override fun getNameIdentifier(): CupSymbolImpl? = firstChild as? CupSymbolImpl

    override fun setName(newName: String): PsiElement? = throw UnsupportedOperationException(
        "Production names are derived from their LHS symbol"
    )

    override fun getPresentation(): ItemPresentation = object : ItemPresentation {
        override fun getPresentableText(): String = name

        override fun getIcon(unused: Boolean): Icon = AllIcons.Nodes.Method
    }
}
