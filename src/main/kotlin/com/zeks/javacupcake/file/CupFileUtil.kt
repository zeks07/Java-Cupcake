package com.zeks.javacupcake.file

import com.zeks.javacupcake.lang.psi.CupLine
import com.zeks.javacupcake.lang.psi.CupTypes

object CupFileUtil {
    @JvmStatic
    val expectedOrder = listOf(
        CupTypes.PACKAGE_SPEC,
        CupTypes.IMPORT_STATEMENT,
        CupTypes.CODE_PARTS,
        CupTypes.SYMBOL_DECLARATION,
        CupTypes.PRECEDENCE_DECLARATION,
        CupTypes.START_SPEC
    )

    @JvmStatic
    fun getElementType(line: CupLine) =
        when {
            line.packageSpec != null -> CupTypes.PACKAGE_SPEC
            line.importStatement != null -> CupTypes.IMPORT_STATEMENT
            line.codeParts != null -> CupTypes.CODE_PARTS
            line.symbolDeclaration != null -> CupTypes.SYMBOL_DECLARATION
            line.precedenceDeclaration != null -> CupTypes.PRECEDENCE_DECLARATION
            line.startSpec != null -> CupTypes.START_SPEC
            else -> null
        }
}