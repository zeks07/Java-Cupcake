package com.zeks.javacupcake.linemarker

import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder
import com.intellij.icons.AllIcons
import com.intellij.psi.PsiElement
import com.intellij.psi.search.LocalSearchScope
import com.intellij.psi.search.searches.ReferencesSearch
import com.zeks.javacupcake.lang.psi.CupNamedNonTerminal
import com.zeks.javacupcake.lang.psi.CupProductionElement
import com.zeks.javacupcake.lang.psi.CupSymbolElement

class CupLineMarker : RelatedItemLineMarkerProvider() {
    override fun collectNavigationMarkers(
        element: PsiElement,
        result: MutableCollection<in RelatedItemLineMarkerInfo<*>>
    ) {
        when (element) {
            is CupProductionElement -> {
                collectNavigationMarkers(element, result)
            }
            is CupNamedNonTerminal -> {
                collectNavigationMarkers(element, result)
            }
        }
    }

    private fun collectNavigationMarkers(
        element: CupProductionElement,
        result: MutableCollection<in RelatedItemLineMarkerInfo<*>>
    ) {
        val symbol = element.firstChild as? CupSymbolElement ?: return
        val reference = symbol.reference.resolve() ?: return

        if (reference == element) return

        val builder = NavigationGutterIconBuilder
            .create(AllIcons.Gutter.ImplementingMethod)
            .setTargets(reference)
            .setTooltipText("Navigate to declaration")

        result.add(builder.createLineMarkerInfo(symbol))
    }

    private fun collectNavigationMarkers(
        element: CupNamedNonTerminal,
        result: MutableCollection<in RelatedItemLineMarkerInfo<*>>
    ) {
        val searchParameters = ReferencesSearch.SearchParameters(
            element,
            LocalSearchScope(element.containingFile),
            false
        )

        val references = ReferencesSearch.search(searchParameters).findAll()
        if (references.isEmpty()) return

        val targets = references.mapNotNull {
            it.element.takeIf { (it as? CupSymbolElement)?.isInDefinition() == true }
        }
        if (targets.isEmpty()) return

        val builder = NavigationGutterIconBuilder
            .create(AllIcons.Gutter.ImplementedMethod)
            .setTargets(targets)
            .setTooltipText("Navigate to definition")

        result.add(builder.createLineMarkerInfo(element))
    }
}
