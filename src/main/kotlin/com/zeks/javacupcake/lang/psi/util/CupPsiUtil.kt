package com.zeks.javacupcake.lang.psi.util

import com.zeks.javacupcake.lang.psi.elements.CupSymbolElement

fun CupSymbolElement.isError(): Boolean = reference.resolve() == null && firstChild.text == "error"