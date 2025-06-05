package com.zeks.javacupcake.formatter

import com.intellij.formatting.Alignment
import com.intellij.formatting.Block
import com.intellij.formatting.Indent
import com.intellij.formatting.SpacingBuilder
import com.intellij.formatting.Wrap
import com.intellij.formatting.WrapType
import com.intellij.lang.ASTNode
import com.intellij.psi.TokenType
import com.intellij.psi.formatter.common.AbstractBlock
import com.zeks.javacupcake.lang.psi.CupTypes

class CupBlock(
    node: ASTNode,
    wrap: Wrap?,
    alignment: Alignment?,
    private val spacingBuilder: SpacingBuilder
) : AbstractBlock(node, wrap, alignment) {

    override fun buildChildren() = generateSequence(myNode.firstChildNode) { it.treeNext }
        .filter { it.elementType != TokenType.WHITE_SPACE }
        .map {
            CupBlock(
                it,
                Wrap.createWrap(WrapType.NONE, false),
                Alignment.createAlignment(),
                spacingBuilder
            )
        }
        .toList()

    override fun getIndent(): Indent? {
        if (node.elementType == CupTypes.RIGHT_HAND_SIDE) {
            return Indent.getNormalIndent()
        }
        return Indent.getNoneIndent()
    }

    override fun getSpacing(child1: Block?, child2: Block) =
        spacingBuilder.getSpacing(this, child1, child2)

    override fun isLeaf() = node.firstChildNode == null

}