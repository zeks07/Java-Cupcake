package com.zeks.javacupcake.codeInsight.fixes

import com.intellij.psi.PsiParserFacade
import com.zeks.javacupcake.lang.file.CupFile
import com.zeks.javacupcake.lang.file.getLines
import com.zeks.javacupcake.lang.psi.CupLine

fun insertLine(
    file: CupFile,
    line: CupLine
) {
    var nextLine: CupLine? = null
    val lines = file.getLines()
    for (currentLine in lines) {
        if (line.lineType < currentLine.lineType) {
            nextLine = currentLine
            break
        }
    }
    val newElement = file.addBefore(line, nextLine)
    val whiteSpaces = PsiParserFacade.getInstance(file.project).createWhiteSpaceFromText("\n\n")
    newElement.parent.addAfter(whiteSpaces, newElement)
}