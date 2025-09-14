package com.zeks.javacupcake.lang.psi

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.openapi.util.TextRange
import com.intellij.psi.LiteralTextEscaper
import com.intellij.psi.PsiLanguageInjectionHost

abstract class CupCodeString(node: ASTNode) : ASTWrapperPsiElement(node), PsiLanguageInjectionHost {
     override fun isValidHost() = true

     override fun updateText(text: String): PsiLanguageInjectionHost? {
         val newElement = CupElementFactory.createJavaCode(project, text)
         return replace(newElement) as? PsiLanguageInjectionHost
     }

     override fun createLiteralTextEscaper(): LiteralTextEscaper<out PsiLanguageInjectionHost?> =
         object : LiteralTextEscaper<CupCodeString>(this) {

             override fun decode(
                 rangeInsideHost: TextRange,
                 outChars: StringBuilder,
             ): Boolean {
                 val text = myHost.text

                 outChars.append(text, rangeInsideHost.startOffset, rangeInsideHost.endOffset)
                 return true
             }

             override fun getOffsetInHost(
                 offsetInDecoded: Int,
                 rangeInsideHost: TextRange,
             ): Int = offsetInDecoded + rangeInsideHost.startOffset

             override fun isOneLine(): Boolean = false
         }
 }