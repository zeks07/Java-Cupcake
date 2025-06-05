package com.zeks.javacupcake.inspection.symbols

import com.zeks.javacupcake.bundle.CupBundle
import com.zeks.javacupcake.inspection.CupInspectionTool

abstract class CupSymbolsInspection : CupInspectionTool() {
    override fun getGroupPath() = arrayOf(
        groupDisplayName,
        CupBundle.message("cup.inspection.symbols.display.name"))
}