package com.zeks.javacupcake.lang.psi.stubs

import com.intellij.openapi.project.Project
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.stubs.StringStubIndexExtension
import com.intellij.psi.stubs.StubIndex
import com.intellij.psi.stubs.StubIndexKey
import com.zeks.javacupcake.lang.psi.elements.CupNamedSymbol

class CupSymbolIndex : StringStubIndexExtension<CupNamedSymbol>() {
    companion object {
        val KEY: StubIndexKey<String, CupNamedSymbol> =
            StubIndexKey.createIndexKey("cup.symbol.index")

        fun findByName(
            name: String,
            project: Project,
            scope: GlobalSearchScope
        ): Collection<CupNamedSymbol> =
            StubIndex.getElements(KEY, name, project, scope, CupNamedSymbol::class.java)
    }

    override fun getKey(): StubIndexKey<String, CupNamedSymbol> = KEY
}