package com.zeks.javacupcake.inspection

import com.intellij.codeInspection.InspectionToolProvider
import com.intellij.codeInspection.LocalInspectionTool
import com.zeks.javacupcake.inspection.lines.CupDuplicateCodePartsInspection
import com.zeks.javacupcake.inspection.lines.CupDuplicateStartInspection
import com.zeks.javacupcake.inspection.lines.CupMissingPackageInspection
import com.zeks.javacupcake.inspection.lines.CupOrderInspection
import com.zeks.javacupcake.inspection.symbols.CupSymbolInStartClauseInspection
import com.zeks.javacupcake.inspection.redundantconstructs.CupRedundantSemicolonInspection
import com.zeks.javacupcake.inspection.symbols.CupDuplicateDeclarationInspection
import com.zeks.javacupcake.inspection.symbols.CupDuplicateProductionInspection
import com.zeks.javacupcake.inspection.symbols.CupNonTerminalInPrecedenceDeclaration
import com.zeks.javacupcake.inspection.symbols.CupUndeclaredSymbolInspection

class CupInspectionProvider : InspectionToolProvider {
    override fun getInspectionClasses(): Array<out Class<out LocalInspectionTool?>?> =
        arrayOf(
            CupOrderInspection::class.java,
            CupMissingPackageInspection::class.java,
            CupDuplicateCodePartsInspection::class.java,
            CupDuplicateStartInspection::class.java,
            CupSymbolInStartClauseInspection::class.java,
            CupNonTerminalInPrecedenceDeclaration::class.java,
            CupUnusedSymbolInspection::class.java,

            CupRedundantSemicolonInspection::class.java,

            CupDuplicateDeclarationInspection::class.java,
            CupDuplicateProductionInspection::class.java,
            CupUndeclaredSymbolInspection::class.java,

            CupClassNameInspection::class.java,
        )
}
