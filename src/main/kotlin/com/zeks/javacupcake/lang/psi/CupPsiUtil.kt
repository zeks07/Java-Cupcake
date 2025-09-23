package com.zeks.javacupcake.lang.psi

fun CupSymbolElement.isError(): Boolean = reference.resolve() == null && firstChild.text == "error"