package com.zeks.javacupcake.lang.psi.stubs

import com.intellij.openapi.project.Project
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.stubs.StringStubIndexExtension
import com.intellij.psi.stubs.StubIndex
import com.intellij.psi.stubs.StubIndexKey
import com.zeks.javacupcake.lang.psi.elements.CupProductionLine

class CupProductionIndex : StringStubIndexExtension<CupProductionLine>() {
    companion object {
        val KEY: StubIndexKey<String, CupProductionLine> = StubIndexKey.createIndexKey("cupcake.production")

        fun findByName(
            name: String,
            project: Project,
            scope: GlobalSearchScope,
        ): Collection<CupProductionLine> =
            StubIndex.getElements(KEY, name, project, scope, CupProductionLine::class.java)
    }

    override fun getKey() = KEY
}