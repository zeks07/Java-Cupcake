package com.zeks.javacupcake.lang.psi.elements

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.zeks.javacupcake.lang.psi.base.CupCodePart
import com.zeks.javacupcake.lang.psi.base.CupCodePartType

abstract class CupAction(node: ASTNode) : ASTWrapperPsiElement(node), CupCodePart {
    override val codePartType
        get() = CupCodePartType.ACTION
}

abstract class CupInit(node: ASTNode) : ASTWrapperPsiElement(node), CupCodePart {
    override val codePartType
        get() = CupCodePartType.INIT
}

abstract class CupParser(node: ASTNode) : ASTWrapperPsiElement(node), CupCodePart {
    override val codePartType
        get() = CupCodePartType.PARSER
}

abstract class CupScan(node: ASTNode) : ASTWrapperPsiElement(node), CupCodePart {
    override val codePartType
        get() = CupCodePartType.SCAN
}
