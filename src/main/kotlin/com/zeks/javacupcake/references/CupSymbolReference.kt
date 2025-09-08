package com.zeks.javacupcake.references

import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementResolveResult
import com.intellij.psi.PsiNamedElement
import com.intellij.psi.PsiPolyVariantReferenceBase
import com.intellij.psi.ResolveResult
import com.intellij.psi.util.PsiTreeUtil
import com.zeks.javacupcake.lang.psi.CupNamedNonTerminal
import com.zeks.javacupcake.lang.psi.CupNamedTerminal
import com.zeks.javacupcake.lang.psi.CupProductionLine
import com.zeks.javacupcake.lang.psi.CupSymbolElement

class CupSymbolReference(element: CupSymbolElement) : PsiPolyVariantReferenceBase<CupSymbolElement>(element) {
    override fun multiResolve(incompleteCode: Boolean): Array<out ResolveResult?> {
        val result =
            findDeclarations(element.text).takeIf { it.isNotEmpty() }
            ?: findDefinitions(element.text)

        return result.map{ PsiElementResolveResult(it) }.toTypedArray()
    }

    private fun findDeclarations(name: String): List<PsiNamedElement> {
        val scope = element.containingFile
        val nonTerminals = PsiTreeUtil.findChildrenOfType(scope, CupNamedNonTerminal::class.java)
            .filter { it.name == name }
        val terminals = PsiTreeUtil.findChildrenOfType(scope, CupNamedTerminal::class.java)
            .filter { it.name == name }
        return nonTerminals + terminals
    }

    private fun findDefinitions(name: String): List<PsiNamedElement> {
        val scope = element.containingFile
        val result = PsiTreeUtil.findChildrenOfType(scope, CupProductionLine::class.java)

        return result.filter { it.name == name }
    }

    override fun isReferenceTo(element: PsiElement) = element == resolve()

    fun isDeclared() = resolve() is CupNamedTerminal || resolve() is CupNamedNonTerminal

    fun isDefined() = resolve() is CupProductionLine

    fun isTerminal() = resolve() is CupNamedTerminal

    fun isNonTerminal() = resolve() is CupNamedNonTerminal || resolve() is CupProductionLine

}