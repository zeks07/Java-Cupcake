package com.zeks.javacupcake.lang.psi

import com.intellij.psi.tree.TokenSet
import com.zeks.javacupcake.lang.psi.CupTypes

object CupTokenSets {
    @JvmStatic
    val KEYWORDS = TokenSet.create(
        CupTypes.IMPORT,
        CupTypes.PACKAGE,
        CupTypes.CODE,
        CupTypes.ACTION,
        CupTypes.PARSER,
        CupTypes.WITH,
        CupTypes.INIT,
        CupTypes.SCAN,
        CupTypes.TERMINAL_,
        CupTypes.NON,
        CupTypes.NONTERMINAL,
        CupTypes.PRECEDENCE,
        CupTypes.LEFT,
        CupTypes.RIGHT,
        CupTypes.NONASSOC,
        CupTypes.START,
        CupTypes.PERCENT_PREC
    )

    @JvmStatic
    val IDENTIFIERS = TokenSet.create(
        CupTypes.IDENTIFIER
    )

    @JvmStatic
    val COMMENTS = TokenSet.create(
        CupTypes.COMMENT
    )

    @JvmStatic
    val SEPARATORS = TokenSet.create(
        CupTypes.OPEN_CODE_STRING,
        CupTypes.CLOSE_CODE_STRING,
        CupTypes.COMMA,
        CupTypes.ASSIGN_OPERATOR,
        CupTypes.BAR,
        CupTypes.LPAREN,
        CupTypes.RPAREN,
        CupTypes.LBRACKET,
        CupTypes.RBRACKET,
        CupTypes.COLON
    )

}
