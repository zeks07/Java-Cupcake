package com.zeks.javacupcake.livetemplates

import com.intellij.codeInsight.template.TemplateActionContext
import com.intellij.codeInsight.template.TemplateContextType
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiUtilCore
import com.zeks.javacupcake.CupBundle
import com.zeks.javacupcake.lang.CupLanguage
import com.zeks.javacupcake.lang.file.CupFile
import com.zeks.javacupcake.lang.file.hasActionCodePart
import com.zeks.javacupcake.lang.file.hasInitCodePart
import com.zeks.javacupcake.lang.file.hasParserCodePart
import com.zeks.javacupcake.lang.file.hasScanCodePart
import com.zeks.javacupcake.lang.file.hasStartSpec

abstract class CupContextType(presentableName: String) : TemplateContextType(presentableName) {
    override fun isInContext(templateActionContext: TemplateActionContext): Boolean {
        val file = templateActionContext.file
        val offset = templateActionContext.startOffset

        var element = file.findElementAt(offset)
        if (element == null) {
            element = file.findElementAt(offset - 1)
        }

        if (!templateActionContext.file.name.endsWith(".cup")) return false

        if (!PsiUtilCore.getLanguageAtOffset(file, offset).isKindOf(CupLanguage)) return false

        return element != null && isInContext(element)
    }

    protected abstract fun isInContext(element: PsiElement): Boolean
}

class Generic : CupContextType(CupBundle.message("template.context.type.generic")) {
    override fun isInContext(element: PsiElement): Boolean {
        return true
    }
}

class TopLevelActionCode : CupContextType(CupBundle.message("template.context.type.top_level_action_code")) {
    override fun isInContext(element: PsiElement): Boolean {
        // Assuming we are in the top level of a file trying to write "a" or "ac",
        // the token will be parsed as PsiErrorElement > Identifier; therefore, we need to check parent.parent
        if (element.parent.parent !is CupFile) return false
        if ((element.containingFile as CupFile).hasActionCodePart()) return false
        return true
    }
}

class TopLevelParserCode : CupContextType(CupBundle.message("template.context.type.top_level_parser_code")) {
    override fun isInContext(element: PsiElement): Boolean {
        if (element.parent.parent !is CupFile) return false
        if ((element.containingFile as CupFile).hasParserCodePart()) return false
        return true
    }
}

class TopLevelScanWith : CupContextType(CupBundle.message("template.context.type.top_level_scan_with")) {
    override fun isInContext(element: PsiElement): Boolean {
        if (element.parent.parent !is CupFile) return false
        if ((element.containingFile as CupFile).hasScanCodePart()) return false
        return true
    }
}

class TopLevelInitWith : CupContextType(CupBundle.message("template.context.type.top_level_init_with")) {
    override fun isInContext(element: PsiElement): Boolean {
        if (element.parent.parent !is CupFile) return false
        if ((element.containingFile as CupFile).hasInitCodePart()) return false
        return true
    }
}

class TopLevelStartWith : CupContextType(CupBundle.message("template.context.type.top_level_start_with")) {
    override fun isInContext(element: PsiElement): Boolean {
        if (element.parent.parent !is CupFile) return false
        if ((element.containingFile as CupFile).hasStartSpec()) return false
        return true
    }
}