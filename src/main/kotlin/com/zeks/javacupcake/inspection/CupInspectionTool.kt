package com.zeks.javacupcake.inspection

import com.intellij.codeInspection.LocalInspectionTool
import com.zeks.javacupcake.bundle.CupBundle

abstract class CupInspectionTool : LocalInspectionTool() {
    override fun getLanguage() = "Cup"

    override fun getGroupDisplayName() = CupBundle.message("cup.inspection.group.display.name")
}