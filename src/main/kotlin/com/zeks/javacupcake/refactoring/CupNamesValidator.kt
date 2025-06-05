package com.zeks.javacupcake.refactoring

import com.intellij.lang.refactoring.NamesValidator
import com.intellij.openapi.project.Project
import com.zeks.javacupcake.lang.CupDictionary

class CupNamesValidator : NamesValidator {
    override fun isKeyword(name: String, project: Project?) = CupDictionary.KEYWORDS.contains(name)

    override fun isIdentifier(name: String, project: Project?) = name.matches(Regex("^[a-zA-Z_][a-zA-Z0-9_]*$"))
}