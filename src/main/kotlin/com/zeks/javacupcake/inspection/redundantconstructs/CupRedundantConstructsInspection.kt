package com.zeks.javacupcake.inspection.redundantconstructs

import com.zeks.javacupcake.bundle.CupBundle
import com.zeks.javacupcake.inspection.CupInspectionTool

abstract class CupRedundantConstructsInspection : CupInspectionTool() {
    override fun getGroupPath() = arrayOf(
        groupDisplayName,
        CupBundle.message("cup.inspection.redundant_constructs.display.name"))
}
