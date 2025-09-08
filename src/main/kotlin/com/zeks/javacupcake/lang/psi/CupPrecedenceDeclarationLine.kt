package com.zeks.javacupcake.lang.psi

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.zeks.javacupcake.lang.file.LineType

abstract class CupPrecedenceDeclarationLine(node: ASTNode) : ASTWrapperPsiElement(node), CupLine {
    override val lineType
        get() = LineType.PRECEDENCE_DECLARATION
}