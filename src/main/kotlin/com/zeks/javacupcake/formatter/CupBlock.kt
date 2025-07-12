package com.zeks.javacupcake.formatter

import com.intellij.formatting.Alignment
import com.intellij.formatting.Block
import com.intellij.formatting.Indent
import com.intellij.formatting.SpacingBuilder
import com.intellij.formatting.Wrap
import com.intellij.formatting.WrapType
import com.intellij.lang.ASTNode
import com.intellij.psi.TokenType
import com.intellij.psi.codeStyle.CodeStyleSettings
import com.intellij.psi.formatter.common.AbstractBlock
import com.zeks.javacupcake.lang.psi.CupTokenSets
import com.zeks.javacupcake.lang.psi.CupTypes

class CupBlock(
    node: ASTNode,
    wrap: Wrap?,
    alignment: Alignment?,
    private val spacingBuilder: SpacingBuilder,
    private val settings: CodeStyleSettings,
) : AbstractBlock(node, wrap, alignment) {

    override fun buildChildren() = generateSequence(myNode.firstChildNode) { it.treeNext }
        .filter { it.elementType != TokenType.WHITE_SPACE }
        .map {
            CupBlock(
                it,
                Wrap.createWrap(WrapType.NONE, false),
                Alignment.createAlignment(),
                spacingBuilder,
                settings,
            )
        }
        .toList()

    override fun getIndent(): Indent? {
        val parentType = myNode.treeParent?.elementType ?: return Indent.getNoneIndent()
        val nodeType = myNode.elementType
        val customSettings = settings.getCustomSettings(CupCodeStyleSettings::class.java)

        if (parentType == CupTypes.CODE_STRING_BLOCK) {
            if (nodeType != CupTypes.CODE_STRING) return Indent.getNoneIndent()
            return Indent.getNormalIndent()
        }

        if (parentType == CupTypes.NON_TERMINAL_DECLARATION) {
            if (CupTokenSets.NON_TERMINAL_DECLARATION.contains(nodeType)) return Indent.getNoneIndent()
            return Indent.getNormalIndent()
        }

        if (parentType == CupTypes.TERMINAL_DECLARATION) {
            if (nodeType == CupTypes.TERMINAL_) return Indent.getNoneIndent()
            return Indent.getNormalIndent()
        }

        if (parentType == CupTypes.PRODUCTION) {
            if (nodeType == CupTypes.SYMBOL) return Indent.getNoneIndent()
            if (nodeType == CupTypes.ASSIGN_OPERATOR) {
                if (customSettings.ASSIGNMENT_OPERATORS_IN_ONE_LINE) return Indent.getNoneIndent()
                if (customSettings.ALIGN_RIGHT_HAND_SIDE_RULES) {
                    return Indent.getSpaceIndent(customSettings.INDENT_SIZE - 2)
                }
                return Indent.getNormalIndent()
            }
            if (nodeType == CupTypes.RIGHT_HAND_SIDE) return Indent.getNormalIndent()
        }

        return Indent.getNoneIndent()
    }

    override fun getSpacing(child1: Block?, child2: Block) =
        spacingBuilder.getSpacing(this, child1, child2)

    override fun isLeaf() = node.firstChildNode == null

}