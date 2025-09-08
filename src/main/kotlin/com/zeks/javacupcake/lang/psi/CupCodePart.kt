package com.zeks.javacupcake.lang.psi

import com.zeks.javacupcake.lang.file.LineType

enum class CupCodePartType {
    ACTION,
    INIT,
    PARSER,
    SCAN,
}

interface CupCodePart : CupLine {
    override val lineType
        get() = LineType.CODE_PARTS

    val codePartType: CupCodePartType
}