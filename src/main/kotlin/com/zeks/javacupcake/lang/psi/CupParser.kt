package com.zeks.javacupcake.lang.psi

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode

abstract class CupParser(node: ASTNode) : ASTWrapperPsiElement(node), CupCodePart {
    override val codePartType
        get() = CupCodePartType.PARSER
}