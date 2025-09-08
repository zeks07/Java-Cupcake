package com.zeks.javacupcake.completion

import com.intellij.codeInsight.completion.CompletionContributor
import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionProvider
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.codeInsight.completion.CompletionType
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.icons.AllIcons
import com.intellij.patterns.PlatformPatterns.psiElement
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiErrorElement
import com.intellij.psi.PsiWhiteSpace
import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.TokenSet
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.util.elementType
import com.intellij.util.ProcessingContext
import com.zeks.javacupcake.completion.context.CodePartContext
import com.zeks.javacupcake.completion.context.CupPositionContextDetector
import com.zeks.javacupcake.completion.context.FileTopLevelContext
import com.zeks.javacupcake.completion.context.PrecedenceDeclarationContext
import com.zeks.javacupcake.completion.context.ProductionContext
import com.zeks.javacupcake.completion.context.StartDeclarationContext
import com.zeks.javacupcake.completion.context.SymbolDeclarationContext
import com.zeks.javacupcake.lang.file.CupFile
import com.zeks.javacupcake.lang.file.LineType
import com.zeks.javacupcake.lang.file.hasActionCodePart
import com.zeks.javacupcake.lang.file.hasInitCodePart
import com.zeks.javacupcake.lang.file.hasPackageDeclaration
import com.zeks.javacupcake.lang.file.hasParserCodePart
import com.zeks.javacupcake.lang.file.hasScanCodePart
import com.zeks.javacupcake.lang.file.hasStartSpec
import com.zeks.javacupcake.lang.psi.CupCodePartType
import com.zeks.javacupcake.lang.psi.CupDeclaredNonTerminal
import com.zeks.javacupcake.lang.psi.CupNamedNonTerminal
import com.zeks.javacupcake.lang.psi.CupNamedTerminal
import com.zeks.javacupcake.lang.psi.CupPrecedenceDeclaration
import com.zeks.javacupcake.lang.psi.CupProduction
import com.zeks.javacupcake.lang.psi.CupSymbol
import com.zeks.javacupcake.lang.psi.CupSymbolDeclarationType
import com.zeks.javacupcake.lang.psi.CupTokenSets
import com.zeks.javacupcake.lang.psi.CupTypes

class CupCompletionContributor : CompletionContributor() {
    init {
        extend(CompletionType.BASIC, psiElement().inside(CupFile::class.java), CupCompletionProvider)
    }
}

private object CupCompletionProvider : CompletionProvider<CompletionParameters>() {
    override fun addCompletions(
        parameters: CompletionParameters,
        context: ProcessingContext,
        result: CompletionResultSet,
    ) {
        val position = parameters.position

        when (val context = CupPositionContextDetector.detect(position)) {
            is FileTopLevelContext -> completeFileTopLevel(result, context)
            is CodePartContext -> completeCodePart(result, context)
            is SymbolDeclarationContext -> completeSymbolDeclaration(result, context)
            is PrecedenceDeclarationContext -> completePrecedenceDeclaration(result, context)
            is StartDeclarationContext -> completeStartDeclaration(result, context)
            is ProductionContext -> completeProduction(result, context)
            else -> return
        }
    }

    private fun completeFileTopLevel(result: CompletionResultSet, context: FileTopLevelContext) {
        val from = context.fromLine
        val to = context.toLine
        for (line in LineType.entries.toTypedArray().slice(from.ordinal..to.ordinal))
            when (line) {
                LineType.PACKAGE -> addPackageDeclarationElement(result, context)
                LineType.IMPORT -> result.addKeyword(CupTypes.IMPORT)
                LineType.CODE_PARTS -> addCodePartsElement(result, context)
                LineType.SYMBOL_DECLARATION -> result.addKeywords(CupTokenSets.SYMBOL_DECLARATION_START)
                LineType.PRECEDENCE_DECLARATION -> result.addKeyword(CupTypes.PRECEDENCE)
                LineType.START_SPEC -> addStartDeclarationElement(result, context)
                LineType.PRODUCTION -> addProductionElement(result, context)
                else -> continue
            }
    }

    private fun addPackageDeclarationElement(result: CompletionResultSet, context: FileTopLevelContext) {
        val file = context.position.containingFile as? CupFile ?: return
        if (!file.hasPackageDeclaration()) result.addKeyword(CupTypes.PACKAGE)
    }

    private fun addCodePartsElement(result: CompletionResultSet, context: FileTopLevelContext) {
        val file = context.position.containingFile as? CupFile ?: return
        if (!file.hasActionCodePart()) result.addKeyword(CupTypes.ACTION)
        if (!file.hasParserCodePart()) result.addKeyword(CupTypes.PARSER)
        if (!file.hasInitCodePart()) result.addKeyword(CupTypes.INIT)
        if (!file.hasScanCodePart()) result.addKeyword(CupTypes.SCAN)
    }

    private fun addStartDeclarationElement(result: CompletionResultSet, context: FileTopLevelContext) {
        val file = context.position.containingFile as? CupFile ?: return
        if (!file.hasStartSpec()) result.addKeyword(CupTypes.START)
    }

    private fun addProductionElement(result: CompletionResultSet, context: FileTopLevelContext) {
        val file = context.position.containingFile as? CupFile ?: return
        val declaredNonTerminals = PsiTreeUtil.findChildrenOfType(file, CupDeclaredNonTerminal::class.java)
            .map { it.text }.toSet()
        val productions = file.children.filter { it is CupProduction }.map { it.firstChild.text }.toSet()

        val resultElements = declaredNonTerminals - productions

        for (element in resultElements) result.addNonTerminal(element)
    }

    private fun completeCodePart(result: CompletionResultSet, context: CodePartContext) {
        val orderInLine = context.orderInLine
        val line = context.line

        if (orderInLine == 2)
            when (line.codePartType) {
                CupCodePartType.ACTION, CupCodePartType.PARSER -> result.addKeyword(CupTypes.CODE)
                CupCodePartType.INIT, CupCodePartType.SCAN -> result.addKeyword(CupTypes.WITH)
            }
    }

    private fun completeSymbolDeclaration(result: CompletionResultSet, context: SymbolDeclarationContext) {
        val position = context.position
        val line = context.line
        val firstChild = line.firstChild

        if (line.symbolDeclarationType != CupSymbolDeclarationType.NON_TERMINAL) return
        if (firstChild.elementType != CupTypes.NON) return
        if (firstChild.nextSiblingInParent()?.firstChild == position) return

        result.addKeyword(CupTypes.TERMINAL_)
    }

    private fun completePrecedenceDeclaration(result: CompletionResultSet, context: PrecedenceDeclarationContext) {
        val position = context.position
        val orderInLine = context.orderInLine
        val file = context.position.containingFile as? CupFile ?: return

        if (orderInLine == 1) return
        if (orderInLine == 2) result.addKeywords(CupTokenSets.PRECEDENCE)

        if (orderInLine >= 4 && position.parent.previousSiblingInParent()?.elementType != CupTypes.COMMA) return

        val terminals = PsiTreeUtil.findChildrenOfType(context.position.containingFile, CupNamedTerminal::class.java)
            .map { it.name }.toMutableSet()

        val precedenceDeclarations = PsiTreeUtil.findChildrenOfType(file, CupPrecedenceDeclaration::class.java)

        for (precedenceDeclaration in precedenceDeclarations) {
            val usedNonTerminals = PsiTreeUtil.findChildrenOfType(precedenceDeclaration, CupSymbol::class.java).map { it.text }
            terminals -= usedNonTerminals.toSet()
        }

        for (nonTerminal in terminals) result.addTerminal(nonTerminal)
    }

    private fun completeStartDeclaration(result: CompletionResultSet, context: StartDeclarationContext) {
        val orderInLine = context.orderInLine
        val file = context.position.containingFile as? CupFile ?: return

        if (orderInLine == 2) result.addKeyword(CupTypes.WITH)
        else if (orderInLine == 3) {
            val productions = PsiTreeUtil.findChildrenOfType(file, CupNamedNonTerminal::class.java).map { it.name }
            for (production in productions) result.addNonTerminal(production)
        }
    }

    private fun completeProduction(result: CompletionResultSet, context: ProductionContext) {
        if (!context.isInRightHandSide) return

        val terminals = PsiTreeUtil.findChildrenOfType(context.position.containingFile, CupNamedTerminal::class.java)
            .map { it.name }.toSet()
        val nonTerminals = PsiTreeUtil.findChildrenOfType(context.position.containingFile, CupNamedNonTerminal::class.java)
            .map { it.name }.toSet()

        for (terminal in terminals) result.addTerminal(terminal)
        for (nonTerminal in nonTerminals) result.addNonTerminal(nonTerminal)
    }

    fun CompletionResultSet.addKeyword(keyword: IElementType) = addElement(
        LookupElementBuilder
            .create(keyword)
            .bold()
    )

    fun CompletionResultSet.addKeywords(keywords: TokenSet) {
        for (keyword in keywords.types) {
            addKeyword(keyword as IElementType)
        }
    }

    fun CompletionResultSet.addNonTerminal(name: String) = addElement(
        LookupElementBuilder
            .create(name)
            .withPresentableText(name)
            .withTypeText("Non-terminal")
            .withIcon(AllIcons.Nodes.Method)
    )

    fun CompletionResultSet.addTerminal(name: String) = addElement(
        LookupElementBuilder
            .create(name)
            .withPresentableText(name)
            .withTypeText("Terminal")
            .withIcon(AllIcons.Nodes.Variable)
    )

    private fun PsiElement.previousSiblingInParent(): PsiElement? {
        val node = PsiTreeUtil.skipSiblingsBackward(this, PsiWhiteSpace::class.java, PsiErrorElement::class.java) ?: return null
        return if (node.parent == parent) node else null
    }

    private fun PsiElement.nextSiblingInParent(): PsiElement? {
        val node = PsiTreeUtil.skipSiblingsForward(this, PsiWhiteSpace::class.java, PsiErrorElement::class.java) ?: return null
        return if (node.parent == parent) node else null
    }
}