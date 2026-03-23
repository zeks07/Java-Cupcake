package com.zeks.javacupcake.lang.psi.elements

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.icons.AllIcons
import com.intellij.lang.ASTNode
import com.intellij.navigation.ItemPresentation
import com.zeks.javacupcake.lang.psi.CupRightHandSide
import com.zeks.javacupcake.lang.psi.impl.CupProductionImpl

abstract class CupRule(node: ASTNode) : ASTWrapperPsiElement(node), CupRightHandSide {

    val leftHandSide: CupProductionImpl get() = parent as CupProductionImpl

    override fun getPresentation() = object : ItemPresentation {
        override fun getPresentableText(): String {
            if (className != null) return "${className!!.text}"

            val siblingRules = leftHandSide.rightHandSideList

            var number = 1
            for (rule in siblingRules) {
                if (rule == this@CupRule) break
                if (rule.className == null) number++;
            }

            return "${leftHandSide.presentation.presentableText}Derived$number"
        }

        override fun getIcon(unused: Boolean) = AllIcons.Nodes.Method
    }
}