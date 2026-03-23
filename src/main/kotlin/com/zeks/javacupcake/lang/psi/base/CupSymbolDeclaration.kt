package com.zeks.javacupcake.lang.psi.base

import com.zeks.javacupcake.lang.file.LineType
import com.zeks.javacupcake.lang.psi.CupTypeName

enum class CupSymbolDeclarationType(val text: String) {
    NON_TERMINAL("non terminal"),
    TERMINAL("terminal");

    override fun toString() = text
}

interface CupSymbolDeclaration : CupLine {
    override val lineType
        get() = LineType.SYMBOL_DECLARATION

    val symbolDeclarationType: CupSymbolDeclarationType

    fun getTypeName(): CupTypeName?
}
