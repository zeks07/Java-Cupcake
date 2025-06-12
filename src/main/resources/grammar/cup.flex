package com.zeks.javacupcake.lang.parser;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.TokenType;
import com.zeks.javacupcake.lang.psi.CupTypes;

%%

%class CupLexer
%implements FlexLexer
%unicode
%function advance
%type IElementType
%eof{
    return;
%eof}

CRLF=\R
WHITE_SPACE=[\ \n\t\f]
IDENTIFIER=[a-zA-Z][a-zA-Z0-9_]*

%state SINGLE_LINE_COMMENT
%state MULTILINE_COMMENT
%state CODE_STRING

%%

<YYINITIAL> {

    package         { return CupTypes.PACKAGE; }
    import          { return CupTypes.IMPORT; }
    code            { return CupTypes.CODE; }
    action          { return CupTypes.ACTION; }
    parser          { return CupTypes.PARSER; }
    with            { return CupTypes.WITH; }
    init            { return CupTypes.INIT; }
    scan            { return CupTypes.SCAN; }
    terminal        { return CupTypes.TERMINAL_; }
    non             { return CupTypes.NON; }
    nonterminal     { return CupTypes.NONTERMINAL; }
    precedence      { return CupTypes.PRECEDENCE; }
    left            { return CupTypes.LEFT; }
    right           { return CupTypes.RIGHT; }
    nonassoc        { return CupTypes.NONASSOC; }
    start           { return CupTypes.START; }
    %prec           { return CupTypes.PERCENT_PREC; }

    \.              { return CupTypes.DOT; }
    \*              { return CupTypes.ASTERISK; }
    ::=             { return CupTypes.ASSIGN_OPERATOR; }
    \|              { return CupTypes.BAR; }
    \(              { return CupTypes.LPAREN; }
    \)              { return CupTypes.RPAREN; }
    \[              { return CupTypes.LBRACKET; }
    \]              { return CupTypes.RBRACKET; }
    ,               { return CupTypes.COMMA; }
    :               { return CupTypes.COLON; }
    ;               { return CupTypes.SEMICOLON; }

    {IDENTIFIER}    { return CupTypes.IDENTIFIER; }

    {WHITE_SPACE}   { return TokenType.WHITE_SPACE; }

    "//"            { yybegin(SINGLE_LINE_COMMENT); return CupTypes.COMMENT; }
    "/*"            { yybegin(MULTILINE_COMMENT); return CupTypes.COMMENT; }

    "{:"            { yybegin(CODE_STRING); return CupTypes.OPEN_CODE_STRING; }

    [^]             { return TokenType.BAD_CHARACTER; }

}

<SINGLE_LINE_COMMENT> {

    {CRLF}          { yybegin(YYINITIAL); return TokenType.WHITE_SPACE; }
    [^]             { return CupTypes.COMMENT; }

}

<MULTILINE_COMMENT> {

    "*/"            { yybegin(YYINITIAL); return CupTypes.COMMENT; }
    [^]             { return CupTypes.COMMENT; }

}

<CODE_STRING> {

    ":}"            { yybegin(YYINITIAL); return CupTypes.CLOSE_CODE_STRING; }
    [^]             { return CupTypes.CODE_STRING; }

}
