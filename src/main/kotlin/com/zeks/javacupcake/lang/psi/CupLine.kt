package com.zeks.javacupcake.lang.psi

import com.intellij.psi.PsiElement
import com.zeks.javacupcake.lang.file.LineType

interface CupLine : PsiElement {
    val lineType: LineType
}