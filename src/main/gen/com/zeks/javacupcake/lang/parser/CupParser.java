// This is a generated file. Not intended for manual editing.
package com.zeks.javacupcake.lang.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import static com.zeks.javacupcake.lang.psi.CupTypes.*;
import static com.intellij.lang.parser.GeneratedParserUtilBase.*;
import com.intellij.psi.tree.IElementType;
import com.intellij.lang.ASTNode;
import com.intellij.psi.tree.TokenSet;
import com.intellij.lang.PsiParser;
import com.intellij.lang.LightPsiParser;

@SuppressWarnings({"SimplifiableIfStatement", "UnusedAssignment"})
public class CupParser implements PsiParser, LightPsiParser {

  public ASTNode parse(IElementType t, PsiBuilder b) {
    parseLight(t, b);
    return b.getTreeBuilt();
  }

  public void parseLight(IElementType t, PsiBuilder b) {
    boolean r;
    b = adapt_builder_(t, b, this, null);
    Marker m = enter_section_(b, 0, _COLLAPSE_, null);
    r = parse_root_(t, b);
    exit_section_(b, 0, m, t, r, true, TRUE_CONDITION);
  }

  protected boolean parse_root_(IElementType t, PsiBuilder b) {
    return parse_root_(t, b, 0);
  }

  static boolean parse_root_(IElementType t, PsiBuilder b, int l) {
    return file(b, l + 1);
  }

  /* ********************************************************** */
  // ACTION CODE
  public static boolean actionCodePart(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "actionCodePart")) return false;
    if (!nextTokenIs(b, ACTION)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, ACTION_CODE_PART, null);
    r = consumeTokens(b, 1, ACTION, CODE);
    p = r; // pin = 1
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // IDENTIFIER
  public static boolean className(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "className")) return false;
    if (!nextTokenIs(b, IDENTIFIER)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, IDENTIFIER);
    exit_section_(b, m, CLASS_NAME, r);
    return r;
  }

  /* ********************************************************** */
  // (actionCodePart | parserCodePart | initCodePart | scanCodePart) codeStringBlock optionalSemicolon?
  public static boolean codeParts(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "codeParts")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, CODE_PARTS, "<code parts>");
    r = codeParts_0(b, l + 1);
    p = r; // pin = 1
    r = r && report_error_(b, codeStringBlock(b, l + 1));
    r = p && codeParts_2(b, l + 1) && r;
    exit_section_(b, l, m, r, p, CupParser::line_recovery);
    return r || p;
  }

  // actionCodePart | parserCodePart | initCodePart | scanCodePart
  private static boolean codeParts_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "codeParts_0")) return false;
    boolean r;
    r = actionCodePart(b, l + 1);
    if (!r) r = parserCodePart(b, l + 1);
    if (!r) r = initCodePart(b, l + 1);
    if (!r) r = scanCodePart(b, l + 1);
    return r;
  }

  // optionalSemicolon?
  private static boolean codeParts_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "codeParts_2")) return false;
    optionalSemicolon(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // OPEN_CODE_STRING CODE_STRING* CLOSE_CODE_STRING
  public static boolean codeStringBlock(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "codeStringBlock")) return false;
    if (!nextTokenIs(b, OPEN_CODE_STRING)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OPEN_CODE_STRING);
    r = r && codeStringBlock_1(b, l + 1);
    r = r && consumeToken(b, CLOSE_CODE_STRING);
    exit_section_(b, m, CODE_STRING_BLOCK, r);
    return r;
  }

  // CODE_STRING*
  private static boolean codeStringBlock_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "codeStringBlock_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!consumeToken(b, CODE_STRING)) break;
      if (!empty_element_parsed_guard_(b, "codeStringBlock_1", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // IDENTIFIER
  public static boolean declaredNonTerminal(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "declaredNonTerminal")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, DECLARED_NON_TERMINAL, "<declared non terminal>");
    r = consumeToken(b, IDENTIFIER);
    exit_section_(b, l, m, r, false, CupParser::list_recovery);
    return r;
  }

  /* ********************************************************** */
  // IDENTIFIER
  public static boolean declaredTerminal(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "declaredTerminal")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, DECLARED_TERMINAL, "<declared terminal>");
    r = consumeToken(b, IDENTIFIER);
    exit_section_(b, l, m, r, false, CupParser::list_recovery);
    return r;
  }

  /* ********************************************************** */
  // line*
  static boolean file(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "file")) return false;
    while (true) {
      int c = current_position_(b);
      if (!line(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "file", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // !(PACKAGE | IMPORT | ACTION | PARSER | INIT | SCAN | TERMINAL_ | NON | NONTERMINAL | PRECEDENCE | START | IDENTIFIER)
  static boolean file_recovery(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "file_recovery")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !file_recovery_0(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // PACKAGE | IMPORT | ACTION | PARSER | INIT | SCAN | TERMINAL_ | NON | NONTERMINAL | PRECEDENCE | START | IDENTIFIER
  private static boolean file_recovery_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "file_recovery_0")) return false;
    boolean r;
    r = consumeToken(b, PACKAGE);
    if (!r) r = consumeToken(b, IMPORT);
    if (!r) r = consumeToken(b, ACTION);
    if (!r) r = consumeToken(b, PARSER);
    if (!r) r = consumeToken(b, INIT);
    if (!r) r = consumeToken(b, SCAN);
    if (!r) r = consumeToken(b, TERMINAL_);
    if (!r) r = consumeToken(b, NON);
    if (!r) r = consumeToken(b, NONTERMINAL);
    if (!r) r = consumeToken(b, PRECEDENCE);
    if (!r) r = consumeToken(b, START);
    if (!r) r = consumeToken(b, IDENTIFIER);
    return r;
  }

  /* ********************************************************** */
  // IDENTIFIER (DOT IDENTIFIER)* (DOT ASTERISK)?
  public static boolean importName(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "importName")) return false;
    if (!nextTokenIs(b, IDENTIFIER)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, IDENTIFIER);
    r = r && importName_1(b, l + 1);
    r = r && importName_2(b, l + 1);
    exit_section_(b, m, IMPORT_NAME, r);
    return r;
  }

  // (DOT IDENTIFIER)*
  private static boolean importName_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "importName_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!importName_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "importName_1", c)) break;
    }
    return true;
  }

  // DOT IDENTIFIER
  private static boolean importName_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "importName_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, DOT, IDENTIFIER);
    exit_section_(b, m, null, r);
    return r;
  }

  // (DOT ASTERISK)?
  private static boolean importName_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "importName_2")) return false;
    importName_2_0(b, l + 1);
    return true;
  }

  // DOT ASTERISK
  private static boolean importName_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "importName_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, DOT, ASTERISK);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // IMPORT importName SEMICOLON
  public static boolean importStatement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "importStatement")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, IMPORT_STATEMENT, "<import statement>");
    r = consumeToken(b, IMPORT);
    p = r; // pin = 1
    r = r && report_error_(b, importName(b, l + 1));
    r = p && consumeToken(b, SEMICOLON) && r;
    exit_section_(b, l, m, r, p, CupParser::line_recovery);
    return r || p;
  }

  /* ********************************************************** */
  // INIT WITH
  public static boolean initCodePart(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "initCodePart")) return false;
    if (!nextTokenIs(b, INIT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, INIT, WITH);
    exit_section_(b, m, INIT_CODE_PART, r);
    return r;
  }

  /* ********************************************************** */
  // IDENTIFIER
  public static boolean label(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "label")) return false;
    if (!nextTokenIs(b, IDENTIFIER)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, IDENTIFIER);
    exit_section_(b, m, LABEL, r);
    return r;
  }

  /* ********************************************************** */
  // packageSpec | importStatement | codeParts | symbolDeclaration | precedenceDeclaration | startSpec | production
  public static boolean line(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "line")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, LINE, "<line>");
    r = packageSpec(b, l + 1);
    if (!r) r = importStatement(b, l + 1);
    if (!r) r = codeParts(b, l + 1);
    if (!r) r = symbolDeclaration(b, l + 1);
    if (!r) r = precedenceDeclaration(b, l + 1);
    if (!r) r = startSpec(b, l + 1);
    if (!r) r = production(b, l + 1);
    exit_section_(b, l, m, r, false, CupParser::file_recovery);
    return r;
  }

  /* ********************************************************** */
  // !(SEMICOLON | PACKAGE | IMPORT | ACTION | PARSER | INIT | SCAN | TERMINAL_ | NON | NONTERMINAL | PRECEDENCE | START | IDENTIFIER )
  static boolean line_recovery(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "line_recovery")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !line_recovery_0(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // SEMICOLON | PACKAGE | IMPORT | ACTION | PARSER | INIT | SCAN | TERMINAL_ | NON | NONTERMINAL | PRECEDENCE | START | IDENTIFIER
  private static boolean line_recovery_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "line_recovery_0")) return false;
    boolean r;
    r = consumeToken(b, SEMICOLON);
    if (!r) r = consumeToken(b, PACKAGE);
    if (!r) r = consumeToken(b, IMPORT);
    if (!r) r = consumeToken(b, ACTION);
    if (!r) r = consumeToken(b, PARSER);
    if (!r) r = consumeToken(b, INIT);
    if (!r) r = consumeToken(b, SCAN);
    if (!r) r = consumeToken(b, TERMINAL_);
    if (!r) r = consumeToken(b, NON);
    if (!r) r = consumeToken(b, NONTERMINAL);
    if (!r) r = consumeToken(b, PRECEDENCE);
    if (!r) r = consumeToken(b, START);
    if (!r) r = consumeToken(b, IDENTIFIER);
    return r;
  }

  /* ********************************************************** */
  // !(COMMA | SEMICOLON)
  static boolean list_recovery(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "list_recovery")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !list_recovery_0(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // COMMA | SEMICOLON
  private static boolean list_recovery_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "list_recovery_0")) return false;
    boolean r;
    r = consumeToken(b, COMMA);
    if (!r) r = consumeToken(b, SEMICOLON);
    return r;
  }

  /* ********************************************************** */
  // NON TERMINAL_
  static boolean nonTerminalAlternative(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "nonTerminalAlternative")) return false;
    if (!nextTokenIs(b, NON)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = consumeTokens(b, 1, NON, TERMINAL_);
    p = r; // pin = 1
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // (nonTerminalAlternative | NONTERMINAL) (typeName declaredNonTerminal (COMMA declaredNonTerminal)* | declaredNonTerminal (COMMA declaredNonTerminal)*) SEMICOLON
  public static boolean nonTerminalDeclaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "nonTerminalDeclaration")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, NON_TERMINAL_DECLARATION, "<non terminal declaration>");
    r = nonTerminalDeclaration_0(b, l + 1);
    p = r; // pin = 1
    r = r && report_error_(b, nonTerminalDeclaration_1(b, l + 1));
    r = p && consumeToken(b, SEMICOLON) && r;
    exit_section_(b, l, m, r, p, CupParser::line_recovery);
    return r || p;
  }

  // nonTerminalAlternative | NONTERMINAL
  private static boolean nonTerminalDeclaration_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "nonTerminalDeclaration_0")) return false;
    boolean r;
    r = nonTerminalAlternative(b, l + 1);
    if (!r) r = consumeToken(b, NONTERMINAL);
    return r;
  }

  // typeName declaredNonTerminal (COMMA declaredNonTerminal)* | declaredNonTerminal (COMMA declaredNonTerminal)*
  private static boolean nonTerminalDeclaration_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "nonTerminalDeclaration_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = nonTerminalDeclaration_1_0(b, l + 1);
    if (!r) r = nonTerminalDeclaration_1_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // typeName declaredNonTerminal (COMMA declaredNonTerminal)*
  private static boolean nonTerminalDeclaration_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "nonTerminalDeclaration_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = typeName(b, l + 1);
    r = r && declaredNonTerminal(b, l + 1);
    r = r && nonTerminalDeclaration_1_0_2(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (COMMA declaredNonTerminal)*
  private static boolean nonTerminalDeclaration_1_0_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "nonTerminalDeclaration_1_0_2")) return false;
    while (true) {
      int c = current_position_(b);
      if (!nonTerminalDeclaration_1_0_2_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "nonTerminalDeclaration_1_0_2", c)) break;
    }
    return true;
  }

  // COMMA declaredNonTerminal
  private static boolean nonTerminalDeclaration_1_0_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "nonTerminalDeclaration_1_0_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && declaredNonTerminal(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // declaredNonTerminal (COMMA declaredNonTerminal)*
  private static boolean nonTerminalDeclaration_1_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "nonTerminalDeclaration_1_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = declaredNonTerminal(b, l + 1);
    r = r && nonTerminalDeclaration_1_1_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (COMMA declaredNonTerminal)*
  private static boolean nonTerminalDeclaration_1_1_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "nonTerminalDeclaration_1_1_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!nonTerminalDeclaration_1_1_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "nonTerminalDeclaration_1_1_1", c)) break;
    }
    return true;
  }

  // COMMA declaredNonTerminal
  private static boolean nonTerminalDeclaration_1_1_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "nonTerminalDeclaration_1_1_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && declaredNonTerminal(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // SEMICOLON
  public static boolean optionalSemicolon(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "optionalSemicolon")) return false;
    if (!nextTokenIs(b, SEMICOLON)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, SEMICOLON);
    exit_section_(b, m, OPTIONAL_SEMICOLON, r);
    return r;
  }

  /* ********************************************************** */
  // IDENTIFIER (DOT IDENTIFIER)*
  public static boolean packageName(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "packageName")) return false;
    if (!nextTokenIs(b, IDENTIFIER)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, IDENTIFIER);
    r = r && packageName_1(b, l + 1);
    exit_section_(b, m, PACKAGE_NAME, r);
    return r;
  }

  // (DOT IDENTIFIER)*
  private static boolean packageName_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "packageName_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!packageName_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "packageName_1", c)) break;
    }
    return true;
  }

  // DOT IDENTIFIER
  private static boolean packageName_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "packageName_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, DOT, IDENTIFIER);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // PACKAGE packageName SEMICOLON
  public static boolean packageSpec(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "packageSpec")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, PACKAGE_SPEC, "<package spec>");
    r = consumeToken(b, PACKAGE);
    p = r; // pin = 1
    r = r && report_error_(b, packageName(b, l + 1));
    r = p && consumeToken(b, SEMICOLON) && r;
    exit_section_(b, l, m, r, p, CupParser::line_recovery);
    return r || p;
  }

  /* ********************************************************** */
  // PARSER CODE
  public static boolean parserCodePart(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "parserCodePart")) return false;
    if (!nextTokenIs(b, PARSER)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, PARSER, CODE);
    exit_section_(b, m, PARSER_CODE_PART, r);
    return r;
  }

  /* ********************************************************** */
  // PERCENT_PREC symbol
  public static boolean precedenceClause(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "precedenceClause")) return false;
    if (!nextTokenIs(b, PERCENT_PREC)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, PERCENT_PREC);
    r = r && symbol(b, l + 1);
    exit_section_(b, m, PRECEDENCE_CLAUSE, r);
    return r;
  }

  /* ********************************************************** */
  // PRECEDENCE (LEFT | RIGHT | NONASSOC) precedenceSymbol (COMMA precedenceSymbol)* SEMICOLON
  public static boolean precedenceDeclaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "precedenceDeclaration")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, PRECEDENCE_DECLARATION, "<precedence declaration>");
    r = consumeToken(b, PRECEDENCE);
    p = r; // pin = 1
    r = r && report_error_(b, precedenceDeclaration_1(b, l + 1));
    r = p && report_error_(b, precedenceSymbol(b, l + 1)) && r;
    r = p && report_error_(b, precedenceDeclaration_3(b, l + 1)) && r;
    r = p && consumeToken(b, SEMICOLON) && r;
    exit_section_(b, l, m, r, p, CupParser::line_recovery);
    return r || p;
  }

  // LEFT | RIGHT | NONASSOC
  private static boolean precedenceDeclaration_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "precedenceDeclaration_1")) return false;
    boolean r;
    r = consumeToken(b, LEFT);
    if (!r) r = consumeToken(b, RIGHT);
    if (!r) r = consumeToken(b, NONASSOC);
    return r;
  }

  // (COMMA precedenceSymbol)*
  private static boolean precedenceDeclaration_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "precedenceDeclaration_3")) return false;
    while (true) {
      int c = current_position_(b);
      if (!precedenceDeclaration_3_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "precedenceDeclaration_3", c)) break;
    }
    return true;
  }

  // COMMA precedenceSymbol
  private static boolean precedenceDeclaration_3_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "precedenceDeclaration_3_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && precedenceSymbol(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // symbol
  public static boolean precedenceSymbol(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "precedenceSymbol")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, PRECEDENCE_SYMBOL, "<precedence symbol>");
    r = symbol(b, l + 1);
    exit_section_(b, l, m, r, false, CupParser::list_recovery);
    return r;
  }

  /* ********************************************************** */
  // symbol ASSIGN_OPERATOR (rightHandSide (BAR rightHandSide)*)? SEMICOLON
  public static boolean production(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "production")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, PRODUCTION, "<production>");
    r = symbol(b, l + 1);
    p = r; // pin = 1
    r = r && report_error_(b, consumeToken(b, ASSIGN_OPERATOR));
    r = p && report_error_(b, production_2(b, l + 1)) && r;
    r = p && consumeToken(b, SEMICOLON) && r;
    exit_section_(b, l, m, r, p, CupParser::line_recovery);
    return r || p;
  }

  // (rightHandSide (BAR rightHandSide)*)?
  private static boolean production_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "production_2")) return false;
    production_2_0(b, l + 1);
    return true;
  }

  // rightHandSide (BAR rightHandSide)*
  private static boolean production_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "production_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = rightHandSide(b, l + 1);
    r = r && production_2_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (BAR rightHandSide)*
  private static boolean production_2_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "production_2_0_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!production_2_0_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "production_2_0_1", c)) break;
    }
    return true;
  }

  // BAR rightHandSide
  private static boolean production_2_0_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "production_2_0_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, BAR);
    r = r && rightHandSide(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // (LPAREN className RPAREN)? (symbol (COLON label)? | codeStringBlock)* precedenceClause?
  public static boolean rightHandSide(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "rightHandSide")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, RIGHT_HAND_SIDE, "<right hand side>");
    r = rightHandSide_0(b, l + 1);
    r = r && rightHandSide_1(b, l + 1);
    r = r && rightHandSide_2(b, l + 1);
    exit_section_(b, l, m, r, false, CupParser::rightHandSide_recovery);
    return r;
  }

  // (LPAREN className RPAREN)?
  private static boolean rightHandSide_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "rightHandSide_0")) return false;
    rightHandSide_0_0(b, l + 1);
    return true;
  }

  // LPAREN className RPAREN
  private static boolean rightHandSide_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "rightHandSide_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LPAREN);
    r = r && className(b, l + 1);
    r = r && consumeToken(b, RPAREN);
    exit_section_(b, m, null, r);
    return r;
  }

  // (symbol (COLON label)? | codeStringBlock)*
  private static boolean rightHandSide_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "rightHandSide_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!rightHandSide_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "rightHandSide_1", c)) break;
    }
    return true;
  }

  // symbol (COLON label)? | codeStringBlock
  private static boolean rightHandSide_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "rightHandSide_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = rightHandSide_1_0_0(b, l + 1);
    if (!r) r = codeStringBlock(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // symbol (COLON label)?
  private static boolean rightHandSide_1_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "rightHandSide_1_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = symbol(b, l + 1);
    r = r && rightHandSide_1_0_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (COLON label)?
  private static boolean rightHandSide_1_0_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "rightHandSide_1_0_0_1")) return false;
    rightHandSide_1_0_0_1_0(b, l + 1);
    return true;
  }

  // COLON label
  private static boolean rightHandSide_1_0_0_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "rightHandSide_1_0_0_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COLON);
    r = r && label(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // precedenceClause?
  private static boolean rightHandSide_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "rightHandSide_2")) return false;
    precedenceClause(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // !(BAR | SEMICOLON)
  static boolean rightHandSide_recovery(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "rightHandSide_recovery")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !rightHandSide_recovery_0(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // BAR | SEMICOLON
  private static boolean rightHandSide_recovery_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "rightHandSide_recovery_0")) return false;
    boolean r;
    r = consumeToken(b, BAR);
    if (!r) r = consumeToken(b, SEMICOLON);
    return r;
  }

  /* ********************************************************** */
  // SCAN WITH
  public static boolean scanCodePart(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "scanCodePart")) return false;
    if (!nextTokenIs(b, SCAN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, SCAN, WITH);
    exit_section_(b, m, SCAN_CODE_PART, r);
    return r;
  }

  /* ********************************************************** */
  // START WITH symbol SEMICOLON
  public static boolean startSpec(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "startSpec")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, START_SPEC, "<start spec>");
    r = consumeTokens(b, 1, START, WITH);
    p = r; // pin = 1
    r = r && report_error_(b, symbol(b, l + 1));
    r = p && consumeToken(b, SEMICOLON) && r;
    exit_section_(b, l, m, r, p, CupParser::line_recovery);
    return r || p;
  }

  /* ********************************************************** */
  // IDENTIFIER
  public static boolean symbol(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "symbol")) return false;
    if (!nextTokenIs(b, IDENTIFIER)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, IDENTIFIER);
    exit_section_(b, m, SYMBOL, r);
    return r;
  }

  /* ********************************************************** */
  // terminalDeclaration | nonTerminalDeclaration
  public static boolean symbolDeclaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "symbolDeclaration")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, SYMBOL_DECLARATION, "<symbol declaration>");
    r = terminalDeclaration(b, l + 1);
    if (!r) r = nonTerminalDeclaration(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // TERMINAL_ (typeName declaredTerminal (COMMA declaredTerminal)* | declaredTerminal (COMMA declaredTerminal)*) SEMICOLON
  public static boolean terminalDeclaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "terminalDeclaration")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, TERMINAL_DECLARATION, "<terminal declaration>");
    r = consumeToken(b, TERMINAL_);
    p = r; // pin = 1
    r = r && report_error_(b, terminalDeclaration_1(b, l + 1));
    r = p && consumeToken(b, SEMICOLON) && r;
    exit_section_(b, l, m, r, p, CupParser::line_recovery);
    return r || p;
  }

  // typeName declaredTerminal (COMMA declaredTerminal)* | declaredTerminal (COMMA declaredTerminal)*
  private static boolean terminalDeclaration_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "terminalDeclaration_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = terminalDeclaration_1_0(b, l + 1);
    if (!r) r = terminalDeclaration_1_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // typeName declaredTerminal (COMMA declaredTerminal)*
  private static boolean terminalDeclaration_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "terminalDeclaration_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = typeName(b, l + 1);
    r = r && declaredTerminal(b, l + 1);
    r = r && terminalDeclaration_1_0_2(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (COMMA declaredTerminal)*
  private static boolean terminalDeclaration_1_0_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "terminalDeclaration_1_0_2")) return false;
    while (true) {
      int c = current_position_(b);
      if (!terminalDeclaration_1_0_2_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "terminalDeclaration_1_0_2", c)) break;
    }
    return true;
  }

  // COMMA declaredTerminal
  private static boolean terminalDeclaration_1_0_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "terminalDeclaration_1_0_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && declaredTerminal(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // declaredTerminal (COMMA declaredTerminal)*
  private static boolean terminalDeclaration_1_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "terminalDeclaration_1_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = declaredTerminal(b, l + 1);
    r = r && terminalDeclaration_1_1_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (COMMA declaredTerminal)*
  private static boolean terminalDeclaration_1_1_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "terminalDeclaration_1_1_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!terminalDeclaration_1_1_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "terminalDeclaration_1_1_1", c)) break;
    }
    return true;
  }

  // COMMA declaredTerminal
  private static boolean terminalDeclaration_1_1_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "terminalDeclaration_1_1_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && declaredTerminal(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // IDENTIFIER (DOT IDENTIFIER)* (LBRACKET RBRACKET)?
  public static boolean typeName(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "typeName")) return false;
    if (!nextTokenIs(b, IDENTIFIER)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, IDENTIFIER);
    r = r && typeName_1(b, l + 1);
    r = r && typeName_2(b, l + 1);
    exit_section_(b, m, TYPE_NAME, r);
    return r;
  }

  // (DOT IDENTIFIER)*
  private static boolean typeName_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "typeName_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!typeName_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "typeName_1", c)) break;
    }
    return true;
  }

  // DOT IDENTIFIER
  private static boolean typeName_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "typeName_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, DOT, IDENTIFIER);
    exit_section_(b, m, null, r);
    return r;
  }

  // (LBRACKET RBRACKET)?
  private static boolean typeName_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "typeName_2")) return false;
    typeName_2_0(b, l + 1);
    return true;
  }

  // LBRACKET RBRACKET
  private static boolean typeName_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "typeName_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, LBRACKET, RBRACKET);
    exit_section_(b, m, null, r);
    return r;
  }

}
