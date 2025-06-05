package com.zeks.javacupcake.lang

import com.intellij.lang.Language

object CupLanguage : Language("Cup") {
    @Suppress("unused")
    private fun readResolve(): Any = CupLanguage
}
