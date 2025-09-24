package com.zeks.javacupcake.codeInsight.inspection

import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.PsiFile
import com.intellij.psi.util.PsiTreeUtil
import com.zeks.javacupcake.lang.psi.CupCodePart
import com.zeks.javacupcake.lang.psi.CupDeclaredNonTerminal
import com.zeks.javacupcake.lang.psi.CupDeclaredTerminal
import com.zeks.javacupcake.lang.psi.CupPrecedenceDeclarationLine
import com.zeks.javacupcake.lang.psi.CupProduction
import com.zeks.javacupcake.lang.psi.CupStartDeclaration
import com.zeks.javacupcake.lang.psi.CupSymbol
import com.zeks.javacupcake.lang.psi.CupVisitor
import com.zeks.javacupcake.lang.references.CupSymbolReference

class CupDuplicateElementsInspection : LocalInspectionTool() {
    private val visitors: List<CupDuplicateElementVisitor> = listOf(
        DuplicateCodePartVisitor,
        DuplicateStartDeclarationVisitor,
        DuplicatePrecedenceVisitor,
        DuplicateSymbolDeclarationVisitor,
        DuplicateProductionVisitor
    )

    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor = object : CupVisitor() {
        override fun visitFile(file: PsiFile) {
            for (visitor in visitors) visitor.inspect(file, holder)
        }
    }

    private object DuplicateCodePartVisitor : CupDuplicateElementVisitor("code_part") {
        override fun inspect(file: PsiFile, holder: ProblemsHolder) {
            val codeParts = PsiTreeUtil.findChildrenOfType(file, CupCodePart::class.java)
            inspectElements(holder, codeParts)
        }

        override fun makeArguments(elements: List<PsiElement>) = listOf(elements.first().firstChild.text)
    }

    private object DuplicateSymbolDeclarationVisitor : CupDuplicateElementVisitor( "symbol") {
        override fun inspect(file: PsiFile, holder: ProblemsHolder) {
            val terminals = PsiTreeUtil.findChildrenOfType(file, CupDeclaredTerminal::class.java)
            val nonTerminals = PsiTreeUtil.findChildrenOfType(file, CupDeclaredNonTerminal::class.java)

            inspectElements(holder, terminals + nonTerminals)
        }

        override fun makeArguments(elements: List<PsiElement>): List<Any?> {
            val message = StringBuilder("\n")
            for (symbol in elements) {
                when (symbol) {
                    is CupDeclaredTerminal -> message.append("terminal ${symbol.text}\n")
                    is CupDeclaredNonTerminal -> message.append("non-terminal ${symbol.text}\n")
                }
            }
            return listOf(message.toString())
        }
    }

    private object DuplicatePrecedenceVisitor : CupDuplicateElementVisitor("precedence") {
        override fun inspect(file: PsiFile, holder: ProblemsHolder) {
            val precedenceLines = PsiTreeUtil.findChildrenOfType(file, CupPrecedenceDeclarationLine::class.java)
            val terminals = mutableListOf<CupSymbol>()
            for (line in precedenceLines) {
                val symbols = PsiTreeUtil.findChildrenOfType(line, CupSymbol::class.java)
                    .filter { (it.reference as? CupSymbolReference)?.isTerminal() == true }
                terminals += symbols
            }
            inspectElements(holder, terminals)
        }

        override fun makeArguments(elements: List<PsiElement>) = listOf(elements.first().text)
    }

    private object DuplicateStartDeclarationVisitor : CupDuplicateElementVisitor("start") {
        override fun inspect(file: PsiFile, holder: ProblemsHolder) {
            val startClauses = PsiTreeUtil.findChildrenOfType(file, CupStartDeclaration::class.java)
            inspectElements(holder, startClauses)
        }
    }

    private object DuplicateProductionVisitor : CupDuplicateElementVisitor("production") {
        override fun inspect(file: PsiFile, holder: ProblemsHolder) {
            val productions = PsiTreeUtil.findChildrenOfType(file, CupProduction::class.java)
                .map { it.firstChild }
            inspectElements(holder, productions)
        }

        override fun makeArguments(elements: List<PsiElement>) = listOf(elements.first().text)
    }
}