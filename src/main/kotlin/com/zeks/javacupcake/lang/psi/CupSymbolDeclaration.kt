package com.zeks.javacupcake.lang.psi

import com.zeks.javacupcake.lang.file.LineType

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