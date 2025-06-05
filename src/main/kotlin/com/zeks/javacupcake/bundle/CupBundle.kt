package com.zeks.javacupcake.bundle

import org.jetbrains.annotations.PropertyKey
import java.text.MessageFormat
import java.util.ResourceBundle

object CupBundle {
    private val BUNDLE = ResourceBundle.getBundle("messages.CupBundle")

    @JvmStatic
    fun message(@PropertyKey(resourceBundle = "messages.CupBundle") key: String, vararg params: Any): String =
        MessageFormat.format(BUNDLE.getString(key), *params)
}
