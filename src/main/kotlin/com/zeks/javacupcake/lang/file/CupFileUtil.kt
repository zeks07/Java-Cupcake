package com.zeks.javacupcake.lang.file

import com.intellij.psi.PsiElement
import com.zeks.javacupcake.lang.psi.CupActionCodePart
import com.zeks.javacupcake.lang.psi.CupCodePart
import com.zeks.javacupcake.lang.psi.CupLine
import com.zeks.javacupcake.lang.psi.CupImportStatement
import com.zeks.javacupcake.lang.psi.CupInitCodePart
import com.zeks.javacupcake.lang.psi.CupPackageSpecLine
import com.zeks.javacupcake.lang.psi.CupParserCodePart
import com.zeks.javacupcake.lang.psi.CupPrecedenceDeclaration
import com.zeks.javacupcake.lang.psi.CupProduction
import com.zeks.javacupcake.lang.psi.CupScanCodePart
import com.zeks.javacupcake.lang.psi.CupStartDeclaration
import com.zeks.javacupcake.lang.psi.CupSymbolDeclaration

enum class LineType {
    PACKAGE,
    IMPORT,
    CODE_PARTS,
    SYMBOL_DECLARATION,
    PRECEDENCE_DECLARATION,
    START_SPEC,
    PRODUCTION,
    EMPTY,
    NULL_VALUE,
}

fun CupFile.getLineType(position: PsiElement) = getLineTypeAtOffset(position.textOffset)

fun CupFile.getLineTypeAtOffset(offset: Int): LineType {
    var element = this.findElementAt(offset) ?: return LineType.NULL_VALUE
    while (element.parent != null && element !is CupLine) {
        element = element.parent
    }
    if (element !is CupLine) return LineType.EMPTY
    return element.lineType
}

fun CupFile.hasPackageDeclaration() = this.children.any { it is CupPackageSpecLine }

fun CupFile.hasActionCodePart() = this.children.any { it is CupActionCodePart }

fun CupFile.hasParserCodePart() = this.children.any { it is CupParserCodePart }

fun CupFile.hasScanCodePart() = this.children.any { it is CupScanCodePart }

fun CupFile.hasInitCodePart() = this.children.any { it is CupInitCodePart }

fun CupFile.hasStartSpec() = this.children.any { it is CupStartDeclaration }

object CupFileUtil {
    @Deprecated("Use LineType directly instead")
    @JvmStatic
    val expectedOrder = listOf(
        LineType.PACKAGE,
        LineType.IMPORT,
        LineType.CODE_PARTS,
        LineType.SYMBOL_DECLARATION,
        LineType.PRECEDENCE_DECLARATION,
        LineType.START_SPEC,
        LineType.PRODUCTION,
    )

    @Deprecated("Use CupFile.getLineTypeAtOffset instead")
    @JvmStatic
    fun getElementLineType(line: CupLine?): LineType =
        when (line) {
            is CupPackageSpecLine -> LineType.PACKAGE
            is CupImportStatement -> LineType.IMPORT
            is CupCodePart -> LineType.CODE_PARTS
            is CupSymbolDeclaration -> LineType.SYMBOL_DECLARATION
            is CupPrecedenceDeclaration -> LineType.PRECEDENCE_DECLARATION
            is CupStartDeclaration -> LineType.START_SPEC
            is CupProduction -> LineType.PRODUCTION
            else -> LineType.EMPTY
        }
}