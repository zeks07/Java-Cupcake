package com.zeks.javacupcake.editor

import com.intellij.lang.ASTNode
import com.intellij.lang.folding.CustomFoldingBuilder
import com.intellij.lang.folding.FoldingDescriptor
import com.intellij.openapi.editor.Document
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.util.endOffset
import com.intellij.psi.util.startOffset
import com.zeks.javacupcake.lang.file.CupFile
import com.zeks.javacupcake.lang.file.getImports
import com.zeks.javacupcake.lang.file.getProductions
import com.zeks.javacupcake.lang.psi.CupCodeStringBlock
import com.zeks.javacupcake.lang.psi.CupPrecedenceDeclaration
import com.zeks.javacupcake.lang.psi.CupProduction
import com.zeks.javacupcake.lang.psi.CupSymbolDeclaration
import com.zeks.javacupcake.lang.psi.impl.CupNonTerminalDeclarationImpl
import com.zeks.javacupcake.lang.psi.impl.CupTerminalDeclarationImpl

class CupFoldingBuilder : CustomFoldingBuilder(), DumbAware {
    override fun buildLanguageFoldRegions(
        descriptors: MutableList<FoldingDescriptor?>,
        root: PsiElement,
        document: Document,
        quick: Boolean,
    ) {
        if (root !is CupFile) return

        foldImports(descriptors, root)
        foldSymbolDeclaration(descriptors, root)
        foldProduction(descriptors, root)
        foldPrecedence(descriptors, root)
        foldCodeString(descriptors, root)
    }

    private fun foldImports(descriptors: MutableList<FoldingDescriptor?>, root: CupFile) {
        val importList = root.getImports() ?: return

        if (importList.children.size < 2) return

        val startOffset = importList.firstChild.firstChild.endOffset + 1
        val endOffset = importList.endOffset

        descriptors.add(FoldingDescriptor(importList, TextRange(startOffset, endOffset)).apply {
            setCanBeRemovedWhenCollapsed(true)
        })
    }

    private fun foldCodeString(descriptors: MutableList<FoldingDescriptor?>, root: CupFile) {
        val codeStrings = PsiTreeUtil.findChildrenOfType(root, CupCodeStringBlock::class.java)

        for (codeString in codeStrings) {
            val startOffset = codeString.textRange.startOffset
            val endOffset = codeString.textRange.endOffset

            descriptors.add(FoldingDescriptor(codeString, TextRange(startOffset, endOffset)))
        }
    }

    private fun foldSymbolDeclaration(descriptors: MutableList<FoldingDescriptor?>, root: CupFile) {
        val symbolDeclarations: List<CupSymbolDeclaration> =
            PsiTreeUtil.findChildrenOfType(root, CupNonTerminalDeclarationImpl::class.java) +
            PsiTreeUtil.findChildrenOfType(root, CupTerminalDeclarationImpl::class.java)

        for (nonTerminalDeclaration in symbolDeclarations) {
            val type = nonTerminalDeclaration.getTypeName()
            val startOffset = if (type != null) {
                type.endOffset + 1
            } else {
                nonTerminalDeclaration.firstChild.endOffset + 1
            }
            val endOffset = nonTerminalDeclaration.endOffset

            descriptors.add(FoldingDescriptor(nonTerminalDeclaration, TextRange(startOffset, endOffset)).apply {
                setCanBeRemovedWhenCollapsed(true)
            })
        }
    }

    private fun foldPrecedence(descriptors: MutableList<FoldingDescriptor?>, root: CupFile) {
        val precedenceDeclarations = PsiTreeUtil.findChildrenOfType(root, CupPrecedenceDeclaration::class.java)

        for (precedenceDeclaration in precedenceDeclarations) {
            val type = precedenceDeclaration.precedenceType ?: continue

            val startOffset = type.endOffset + 1
            val endOffset = precedenceDeclaration.endOffset

            descriptors.add(FoldingDescriptor(precedenceDeclaration, TextRange(startOffset, endOffset)).apply {
                setCanBeRemovedWhenCollapsed(true)
            })
        }
    }

    private fun foldProduction(descriptors: MutableList<FoldingDescriptor?>, root: CupFile) {
        val productions = root.getProductions()

        for (production in productions) {
            val startOffset = production.assign.endOffset
            val endOffset = production.endOffset

            descriptors.add(FoldingDescriptor(production, TextRange(startOffset, endOffset)).apply {
                setCanBeRemovedWhenCollapsed(true)
            })
        }
    }

    override fun getLanguagePlaceholderText(node: ASTNode, range: TextRange) = when (node.psi) {
        is CupCodeStringBlock -> "{: ... :}"
        else -> "..."
    }

    override fun isRegionCollapsedByDefault(node: ASTNode) = when (node.psi) {
        is CupProduction -> false
        is CupSymbolDeclaration -> false
        else -> true
    }
}