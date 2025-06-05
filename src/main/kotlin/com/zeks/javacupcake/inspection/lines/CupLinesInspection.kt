package com.zeks.javacupcake.inspection.lines

import com.zeks.javacupcake.bundle.CupBundle
import com.zeks.javacupcake.inspection.CupInspectionTool

abstract class CupLinesInspection : CupInspectionTool() {
    override fun getGroupPath() = arrayOf(
        groupDisplayName,
        CupBundle.message("cup.inspection.lines.display.name"))
}