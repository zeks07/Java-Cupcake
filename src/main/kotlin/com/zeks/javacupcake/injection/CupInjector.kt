package com.zeks.javacupcake.injection

import com.intellij.lang.injection.MultiHostInjector
import com.intellij.lang.injection.MultiHostRegistrar
import com.intellij.lang.java.JavaLanguage
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.zeks.javacupcake.lang.file.CupFile
import com.zeks.javacupcake.lang.file.getPackage
import com.zeks.javacupcake.lang.psi.CupActionCodePart
import com.zeks.javacupcake.lang.psi.CupCodeStringBlock
import com.zeks.javacupcake.lang.psi.CupInitCodePart
import com.zeks.javacupcake.lang.psi.CupParserCodePart
import com.zeks.javacupcake.lang.psi.CupRightHandSide
import com.zeks.javacupcake.lang.psi.CupScanCodePart
import com.zeks.javacupcake.lang.psi.impl.CupLabelImpl
import kotlin.reflect.KClass

class CupInjector : MultiHostInjector {
    override fun getLanguagesToInject(
        registrar: MultiHostRegistrar,
        context: PsiElement,
    ) {
        val file = context.containingFile

        if (file !is CupFile) return
        if (context !is CupCodeStringBlock) return

        val text = context.text.substring(2, context.text.length - 2)
        if (text.isBlank()) return
        val firstNonWhitespace = text.indexOfFirst { it != ' ' && it != '\t' }
        val whiteSpacesBefore = if (text[firstNonWhitespace] == '\n') firstNonWhitespace + 1 else 0

        val lastNonWhitespace = text.indexOfLast { it != ' ' && it != '\t' }
        val whiteSpacesAfter = if (text[lastNonWhitespace] == '\n') text.length - lastNonWhitespace - 1 else 0

        val packageName = file.getPackage()?.packageName?.text ?: ""
        val parent = context.parent
        val section = Sections.from(parent) ?: return

        val prefix = buildString {
            appendLine("package $packageName;")
            appendLine(section.affixes.first)
            if (parent is CupRightHandSide) {
                appendLine("Object RESULT;")
                for (child in parent.children) {
                    if (child is CupLabelImpl) {
                        val symbol = child.firstChild.text
                        appendLine("    int $symbol,${symbol}right; Object $symbol;")
                    }
                    if (child == context) break
                }
            }
        }
        val suffix = section.affixes.second

        registrar
            .startInjecting(JavaLanguage.INSTANCE)
            .addPlace(
                prefix,
                suffix,
                context,
                TextRange(2 + whiteSpacesBefore, context.text.length - 2 - whiteSpacesAfter)
            )
            .doneInjecting()
    }

    override fun elementsToInjectIn(): List<Class<out PsiElement?>?> = listOf(CupCodeStringBlock::class.java)
}

private val beforeInitWith = buildString {
    appendLine("public class parser extends java_cup.runtime.lr_parser {")
    appendLine("    public parser() {}")
    appendLine("    public parser(java_cup.runtime.Scanner s) {}")
    appendLine("    protected static final short _production_table[][];")
    appendLine("    public short[][] production_table() {}")
    appendLine("    protected static final short[][] _action_table;")
    appendLine("    public short[][] action_table() {}")
    appendLine("    protected static final short[][] _reduce_table;")
    appendLine("    public short[][] reduce_table() {}")
    appendLine("    protected CUP\$parser\$actions action_obj;")
    appendLine("    protected void init_actions() {}")
    appendLine("    public java_cup.runtime.Symbol do_action(int act_num, java_cup.runtime.lr_parser parser, java.util.Stack stack, int top) throws java.lang.Exception {}")
    appendLine("    public int start_state() {}")
    appendLine("    public int start_production() {}")
    appendLine("    public int EOF_sym() {}")
    appendLine("    public int error_sym() {}")
    appendLine("    public void user_init() throws java.lang.Exception {")
}

private val beforeScanWith = buildString {
    appendLine("    }")
    appendLine("    public java_cup.runtime.Symbol scan() throws java.lang.Exception {")
}

private val beforeCodeWith = buildString {
    appendLine("    }")
}

private val beforeActionCode = buildString {
    appendLine("}")
    appendLine("public class CUP\$parser\$actions {")
}

private val beforeProduction = buildString {
    appendLine("    private final parser parser;")
    appendLine("    CUP\$parser\$actions(parser parser) {}")
    appendLine("    public final java_cup.runtime.Symbol CUP\$parser\$do_action(int CUP\$parser\$act_num, java_cup.runtime.lr_parser CUP\$parser\$parser, java.util.Stack CUP\$parser\$stack, int CUP\$parser\$top) throws java.lang.Exception {")
    appendLine("    java_cup.runtime.Symbol CUP\$parser\$result;")
}

private val finally = buildString {
    appendLine("    }")
    appendLine("}")
}

private enum class Sections(val partClass: KClass<out PsiElement>, val affixes: Pair<String, String>) {
    INIT(CupInitCodePart::class, beforeInitWith to beforeScanWith + beforeCodeWith + beforeActionCode + beforeProduction + finally),
    SCAN(CupScanCodePart::class, beforeInitWith + beforeScanWith to beforeCodeWith + beforeActionCode + beforeProduction + finally),
    PARSER(CupParserCodePart::class, beforeInitWith + beforeScanWith + beforeCodeWith to beforeActionCode + beforeProduction + finally),
    ACTION(CupActionCodePart::class, beforeInitWith + beforeScanWith + beforeCodeWith + beforeActionCode to beforeProduction + finally),
    PRODUCTION(CupRightHandSide::class, beforeInitWith + beforeScanWith + beforeCodeWith + beforeActionCode + beforeProduction to finally);

    companion object {
        fun from(element: PsiElement) = entries.find { it.partClass.isInstance(element) }
    }
}