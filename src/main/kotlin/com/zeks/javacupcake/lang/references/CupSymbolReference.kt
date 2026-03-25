package com.zeks.javacupcake.lang.references

import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementResolveResult
import com.intellij.psi.PsiNamedElement
import com.intellij.psi.PsiPolyVariantReferenceBase
import com.intellij.psi.ResolveResult
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.stubs.StubIndex
import com.intellij.psi.util.PsiTreeUtil
import com.zeks.javacupcake.lang.psi.elements.CupNamedNonTerminal
import com.zeks.javacupcake.lang.psi.elements.CupNamedSymbol
import com.zeks.javacupcake.lang.psi.elements.CupNamedTerminal
import com.zeks.javacupcake.lang.psi.elements.CupProductionLine
import com.zeks.javacupcake.lang.psi.elements.CupSymbolElement
import com.zeks.javacupcake.lang.psi.stubs.CupProductionIndex
import com.zeks.javacupcake.lang.psi.stubs.CupSymbolIndex

class CupSymbolReference(element: CupSymbolElement) : PsiPolyVariantReferenceBase<CupSymbolElement>(element) {
    override fun multiResolve(incompleteCode: Boolean): Array<out ResolveResult> {
        val name = element.text
        val declarations = findDeclarations(name)
        val definitions = findDefinitions(name)

        val results = declarations.ifEmpty { definitions } +
                if (declarations.isEmpty()) definitions else emptyList()

        return results.distinct()
            .map { PsiElementResolveResult(it) }
            .toTypedArray()
    }

    // For "go to definition" navigation
    fun resolveDefinitionFirst(): Array<out ResolveResult> {
        if (element.isInDefinition()) return multiResolve(false)

        val name = element.text
        val declarations = findDeclarations(name)
        val definitions = findDefinitions(name)

        val results = definitions.ifEmpty { declarations } +
                if (definitions.isEmpty()) declarations else emptyList()

        return results.distinct()
            .map { PsiElementResolveResult(it) }
            .toTypedArray()
    }

    private fun findDeclarations(name: String): List<PsiNamedElement> {
        val scope = GlobalSearchScope.fileScope(element.containingFile)
        val project = element.project
        return buildList {
            addAll(StubIndex.getElements(CupSymbolIndex.KEY, name, project, scope, CupNamedSymbol::class.java))
        }
    }

    private fun findDefinitions(name: String): List<PsiNamedElement> {
        val scope = GlobalSearchScope.fileScope(element.containingFile)
        return CupProductionIndex.findByName(name, element.project, scope).toList()
    }

    fun isDeclared(): Boolean {
        val resolved = multiResolve(false).map { it.element }
        return resolved.any { it is CupNamedTerminal || it is CupNamedNonTerminal}
    }

    fun isDefined() = multiResolve(false).any { it.element is CupProductionLine }

    fun isTerminal() = multiResolve(false).any { it.element is CupNamedTerminal }

    fun isNonTerminal() = multiResolve(false).any { it.element is CupNamedNonTerminal || it.element is CupProductionLine }

    override fun isReferenceTo(element: PsiElement) = multiResolve(false).any { it.element == element }

}