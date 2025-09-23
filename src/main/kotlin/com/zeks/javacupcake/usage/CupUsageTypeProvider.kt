package com.zeks.javacupcake.usage

import com.intellij.psi.PsiElement
import com.intellij.usages.impl.rules.UsageType
import com.intellij.usages.impl.rules.UsageTypeProvider
import com.zeks.javacupcake.CupBundle
import com.zeks.javacupcake.lang.psi.CupSymbolElement

class CupUsageTypeProvider : UsageTypeProvider {
    override fun getUsageType(element: PsiElement): UsageType? {
        val usage = element as? CupSymbolElement ?: return null

        return when {
            usage.isInProduction() -> UsageType { CupBundle.message("usage.type.right_hand_side") }
            usage.isInDefinition() -> UsageType { CupBundle.message("usage.type.left_hand_side") }
            usage.isInStartClause() -> UsageType { CupBundle.message("usage.type.start_clause") }
            else -> null
        }
    }
}
