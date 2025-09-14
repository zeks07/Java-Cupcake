package com.zeks.javacupcake.annotation

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.psi.PsiElement
import com.zeks.javacupcake.highlighting.CupHighlightColors
import com.zeks.javacupcake.lang.psi.CupDeclaredNonTerminal
import com.zeks.javacupcake.lang.psi.CupDeclaredTerminal
import com.zeks.javacupcake.lang.psi.CupSymbolElement

class CupAnnotator : Annotator {
    override fun annotate(element: PsiElement, holder: AnnotationHolder) {
        when (element) {
            is CupDeclaredTerminal -> {
                holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
                    .textAttributes(CupHighlightColors.TERMINAL)
                    .range(element.firstChild.textRange)
                    .create()
            }
            is CupDeclaredNonTerminal -> {
                holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
                    .textAttributes(CupHighlightColors.NON_TERMINAL)
                    .range(element.firstChild.textRange)
                    .create()
            }
            is CupSymbolElement -> {
                when {
                    element.reference.isTerminal() -> {
                        holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
                            .textAttributes(CupHighlightColors.TERMINAL)
                            .create()
                    }

                    element.reference.isNonTerminal() -> {
                        holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
                            .textAttributes(CupHighlightColors.NON_TERMINAL)
                            .create()
                    }
                }
            }
        }
    }
}
