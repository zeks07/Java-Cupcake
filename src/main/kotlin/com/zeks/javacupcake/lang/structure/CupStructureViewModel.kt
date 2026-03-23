package com.zeks.javacupcake.lang.structure

import com.intellij.ide.structureView.StructureViewModel
import com.intellij.ide.structureView.StructureViewModelBase
import com.intellij.ide.structureView.StructureViewTreeElement
import com.intellij.openapi.editor.Editor
import com.zeks.javacupcake.lang.file.CupFile
import com.zeks.javacupcake.lang.psi.impl.CupProductionImpl

class CupStructureViewModel (
    editor: Editor?,
    cupFile: CupFile,
) : StructureViewModelBase(
    cupFile,
    editor,
    CupStructureViewElement(cupFile)
), StructureViewModel.ElementInfoProvider {
    override fun isAlwaysShowsPlus(element: StructureViewTreeElement?) = false

    override fun isAlwaysLeaf(element: StructureViewTreeElement?) = when (element?.value) {
        is CupFile -> false
        is CupProductionImpl -> false
        else -> true
    }
}