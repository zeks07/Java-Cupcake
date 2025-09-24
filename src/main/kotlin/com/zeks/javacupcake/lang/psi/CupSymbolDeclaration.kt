package com.zeks.javacupcake.lang.psi

import com.zeks.javacupcake.lang.file.LineType

enum class CupSymbolDeclarationType {
    NON_TERMINAL,
    TERMINAL,
}

interface CupSymbolDeclaration : CupLine {
    override val lineType
        get() = LineType.SYMBOL_DECLARATION

    val symbolDeclarationType: CupSymbolDeclarationType

    fun getTypeName(): CupTypeName?
}