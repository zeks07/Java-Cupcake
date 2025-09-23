package com.zeks.javacupcake.completion.context

import com.intellij.psi.PsiElement
import com.intellij.psi.PsiErrorElement
import com.intellij.psi.PsiWhiteSpace
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.util.childLeafs
import com.intellij.psi.util.elementType
import com.zeks.javacupcake.lang.file.CupFile
import com.zeks.javacupcake.lang.file.LineType
import com.zeks.javacupcake.lang.file.getLineType
import com.zeks.javacupcake.lang.psi.CupClassName
import com.zeks.javacupcake.lang.psi.CupCodePart
import com.zeks.javacupcake.lang.psi.CupLine
import com.zeks.javacupcake.lang.psi.CupPrecedenceDeclarationLine
import com.zeks.javacupcake.lang.psi.CupProductionLine
import com.zeks.javacupcake.lang.psi.CupRightHandSide
import com.zeks.javacupcake.lang.psi.CupSymbolDeclaration
import com.zeks.javacupcake.lang.psi.impl.CupStartDeclarationImpl

sealed class CupPositionContext {
    abstract val position: PsiElement
}

sealed class CupLineContext() : CupPositionContext() {
    abstract val line: CupLine
    val orderInLine: Int
        get() {
            var order = 1
            for (node in line.childLeafs()) {
                if (node is PsiWhiteSpace) continue
                if (node is PsiErrorElement) return -1
                if (node == position) break
                order++
            }
            return order
        }
}

class PackageDeclarationContext(
    override val position: PsiElement,
) : CupPositionContext()

class ImportStatementContext(
    override val position: PsiElement,
) : CupPositionContext()

class CodePartContext(
    override val position: PsiElement,
    override val line: CupCodePart,
) : CupLineContext()

class SymbolDeclarationContext(
    override val position: PsiElement,
    override val line: CupSymbolDeclaration,
) : CupLineContext()

class PrecedenceDeclarationContext(
    override val position: PsiElement,
    override val line: CupPrecedenceDeclarationLine,
) : CupLineContext()

class StartDeclarationContext(
    override val position: PsiElement,
    override val line: CupStartDeclarationImpl,
) : CupLineContext()

class ProductionContext(
    override val position: PsiElement,
    override val line: CupProductionLine
) : CupLineContext() {
    val isInRightHandSide: Boolean
        get() {
            var current = position
            while (current !is CupRightHandSide) {
                current = current.parent ?: return false
            }
            return true
        }

    val isInClassName: Boolean
        get() {
            var current = position
            while (current !is CupClassName) {
                current = current.parent ?: return false
            }
            return true
        }
}

class FileTopLevelContext(
    override val position: PsiElement,
    val fromLine: LineType,
    val toLine: LineType,
) : CupPositionContext() {
    override fun toString(): String {
        return "FileTopLevelContext(fromLine=$fromLine, toLine=$toLine)"
    }
}

class UnknownPositionContext(
    override val position: PsiElement,
) : CupPositionContext()

object CupPositionContextDetector {
    fun detect(position: PsiElement): CupPositionContext {
        val line = getLine(position) ?: return fileTopLevelContext(position)

        return when (line.lineType) {
            LineType.PACKAGE -> packageDeclarationContext(position)
            LineType.IMPORT -> importStatementContext(position)
            LineType.CODE_PARTS -> CodePartContext(position, line as CupCodePart)
            LineType.SYMBOL_DECLARATION -> SymbolDeclarationContext(position, line as CupSymbolDeclaration)
            LineType.PRECEDENCE_DECLARATION -> PrecedenceDeclarationContext(position, line as CupPrecedenceDeclarationLine)
            LineType.START_SPEC -> StartDeclarationContext(position, line as CupStartDeclarationImpl)
            LineType.PRODUCTION -> ProductionContext(position, line as CupProductionLine)

            else -> UnknownPositionContext(position)
        }
    }

    private fun getLine(position: PsiElement): CupLine? {
        var current = position
        while (current !is CupLine) {
            current = current.parent ?: return null
        }
        return current
    }

    private fun packageDeclarationContext(position: PsiElement): PackageDeclarationContext {
        return PackageDeclarationContext(position)
    }

    private fun importStatementContext(position: PsiElement): ImportStatementContext {
        return ImportStatementContext(position)
    }

    private fun fileTopLevelContext(position: PsiElement): CupPositionContext {
        val previousSibling = PsiTreeUtil.skipSiblingsBackward(position.parent, PsiWhiteSpace::class.java)
        val nextSibling = PsiTreeUtil.skipSiblingsForward(position.parent, PsiWhiteSpace::class.java)
        val file = position.containingFile as CupFile

        val fromLine = if (previousSibling == null) {
            LineType.PACKAGE
        } else when (val type = file.getLineType(previousSibling)) {
            LineType.EMPTY -> LineType.PACKAGE
            LineType.PACKAGE -> LineType.IMPORT
            LineType.START_SPEC -> LineType.PRODUCTION
            else -> type
        }

        val toLine = if (nextSibling == null) {
            LineType.PRODUCTION
        } else when (val type = file.getLineType(nextSibling)) {
            LineType.START_SPEC -> LineType.PRECEDENCE_DECLARATION
            LineType.EMPTY -> LineType.PRODUCTION
            else -> type
        }

        return FileTopLevelContext(
            position,
            fromLine,
            toLine,
        )
    }
}