package com.zeks.javacupcake.lang.file

import com.intellij.psi.PsiElement
import com.zeks.javacupcake.lang.psi.CupActionCodePart
import com.zeks.javacupcake.lang.psi.CupLine
import com.zeks.javacupcake.lang.psi.CupImportStatements
import com.zeks.javacupcake.lang.psi.CupInitCodePart
import com.zeks.javacupcake.lang.psi.CupPackageSpecLine
import com.zeks.javacupcake.lang.psi.CupParserCodePart
import com.zeks.javacupcake.lang.psi.CupProduction
import com.zeks.javacupcake.lang.psi.CupScanCodePart
import com.zeks.javacupcake.lang.psi.CupStartDeclaration
import com.zeks.javacupcake.lang.psi.impl.CupPackageSpecImpl

enum class LineType(val text: String) {
    EMPTY(""),
    PACKAGE("package declaration"),
    IMPORT("import statement"),
    CODE_PARTS("code part"),
    SYMBOL_DECLARATION("symbol declaration"),
    PRECEDENCE_DECLARATION("precedence declaration"),
    START_SPEC("start declaration"),
    PRODUCTION("production"),
    NULL_VALUE("null");

    override fun toString() = text
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

fun CupFile.getLines() = this.children.filterIsInstance<CupLine>()

fun CupFile.getPackage() = (this.children.find { it is CupPackageSpecLine } as? CupPackageSpecImpl)

fun CupFile.getImports() = this.children.filterIsInstance<CupImportStatements>().firstOrNull()

fun CupFile.getProductions() = this.children.filterIsInstance<CupProduction>()