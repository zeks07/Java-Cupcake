package com.zeks.javacupcake.lang.psi

import com.intellij.psi.tree.TokenSet

object CupTokenSets {
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
        CupTypes.PERCENT_PREC,
    )

    val IDENTIFIERS = TokenSet.create(
        CupTypes.IDENTIFIER,
    )

    val COMMENTS = TokenSet.create(
        CupTypes.COMMENT,
    )

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
        CupTypes.COLON,
    )

    val CODE_PART_FIRST = TokenSet.create(
        CupTypes.ACTION,
        CupTypes.PARSER,
        CupTypes.INIT,
        CupTypes.SCAN,
    )

    val CODE_PART_SECOND = TokenSet.create(
        CupTypes.CODE,
        CupTypes.WITH,
    )

    val NON_TERMINAL_DECLARATION = TokenSet.create(
        CupTypes.NON,
        CupTypes.TERMINAL_,
        CupTypes.NONTERMINAL,
    )

    val SYMBOL_DECLARATION_START = TokenSet.create(
        CupTypes.NON,
        CupTypes.TERMINAL_,
        CupTypes.NONTERMINAL,
    )

    val PRECEDENCE = TokenSet.create(
        CupTypes.LEFT,
        CupTypes.RIGHT,
        CupTypes.NONASSOC,
    )

    val CODE_PARTS = TokenSet.create(
        CupTypes.ACTION_CODE_PART,
        CupTypes.PARSER_CODE_PART,
        CupTypes.INIT_CODE_PART,
        CupTypes.SCAN_CODE_PART,
    )

    val LINE = TokenSet.create(
        CupTypes.PACKAGE_SPEC,
        CupTypes.IMPORT_STATEMENT,
        CupTypes.ACTION_CODE_PART,
        CupTypes.PARSER_CODE_PART,
        CupTypes.INIT_CODE_PART,
        CupTypes.SCAN_CODE_PART,
        CupTypes.TERMINAL_DECLARATION,
        CupTypes.NON_TERMINAL_DECLARATION,
        CupTypes.PRECEDENCE_DECLARATION,
        CupTypes.START_DECLARATION,
        CupTypes.PRODUCTION,
    )
}
