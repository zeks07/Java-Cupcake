// This is a generated file. Not intended for manual editing.
package com.zeks.javacupcake.lang.psi;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import com.zeks.javacupcake.lang.psi.impl.*;

public interface CupTypes {

  IElementType ACTION_CODE_PART = new CupElementType("ACTION_CODE_PART");
  IElementType CLASS_NAME = new CupElementType("CLASS_NAME");
  IElementType CODE_STRING_BLOCK = new CupElementType("CODE_STRING_BLOCK");
  IElementType DECLARED_NON_TERMINAL = new CupElementType("DECLARED_NON_TERMINAL");
  IElementType DECLARED_TERMINAL = new CupElementType("DECLARED_TERMINAL");
  IElementType IMPORT_NAME = new CupElementType("IMPORT_NAME");
  IElementType IMPORT_STATEMENT = new CupElementType("IMPORT_STATEMENT");
  IElementType INIT_CODE_PART = new CupElementType("INIT_CODE_PART");
  IElementType LABEL = new CupElementType("LABEL");
  IElementType NON_TERMINAL_DECLARATION = new CupElementType("NON_TERMINAL_DECLARATION");
  IElementType OPTIONAL_SEMICOLON = new CupElementType("OPTIONAL_SEMICOLON");
  IElementType PACKAGE_NAME = new CupElementType("PACKAGE_NAME");
  IElementType PACKAGE_SPEC = new CupElementType("PACKAGE_SPEC");
  IElementType PARSER_CODE_PART = new CupElementType("PARSER_CODE_PART");
  IElementType PRECEDENCE_CLAUSE = new CupElementType("PRECEDENCE_CLAUSE");
  IElementType PRECEDENCE_DECLARATION = new CupElementType("PRECEDENCE_DECLARATION");
  IElementType PRECEDENCE_SYMBOL = new CupElementType("PRECEDENCE_SYMBOL");
  IElementType PRODUCTION = new CupElementType("PRODUCTION");
  IElementType RIGHT_HAND_SIDE = new CupElementType("RIGHT_HAND_SIDE");
  IElementType SCAN_CODE_PART = new CupElementType("SCAN_CODE_PART");
  IElementType START_DECLARATION = new CupElementType("START_DECLARATION");
  IElementType SYMBOL = new CupElementType("SYMBOL");
  IElementType TERMINAL_DECLARATION = new CupElementType("TERMINAL_DECLARATION");
  IElementType TYPE_NAME = new CupElementType("TYPE_NAME");

  IElementType ACTION = new CupTokenType("action");
  IElementType ASSIGN_OPERATOR = new CupTokenType("::=");
  IElementType ASTERISK = new CupTokenType("*");
  IElementType BAR = new CupTokenType("|");
  IElementType CLOSE_CODE_STRING = new CupTokenType(":}");
  IElementType CODE = new CupTokenType("code");
  IElementType CODE_STRING = new CupTokenType("CODE_STRING");
  IElementType COLON = new CupTokenType(":");
  IElementType COMMA = new CupTokenType(",");
  IElementType COMMENT = new CupTokenType("comment");
  IElementType DOT = new CupTokenType(".");
  IElementType IDENTIFIER = new CupTokenType("identifier");
  IElementType IMPORT = new CupTokenType("import");
  IElementType INIT = new CupTokenType("init");
  IElementType LBRACKET = new CupTokenType("[");
  IElementType LEFT = new CupTokenType("left");
  IElementType LPAREN = new CupTokenType("(");
  IElementType NON = new CupTokenType("non");
  IElementType NONASSOC = new CupTokenType("nonassoc");
  IElementType NONTERMINAL = new CupTokenType("nonterminal");
  IElementType OPEN_CODE_STRING = new CupTokenType("{:");
  IElementType PACKAGE = new CupTokenType("package");
  IElementType PARSER = new CupTokenType("parser");
  IElementType PERCENT_PREC = new CupTokenType("%prec");
  IElementType PRECEDENCE = new CupTokenType("precedence");
  IElementType RBRACKET = new CupTokenType("]");
  IElementType RIGHT = new CupTokenType("right");
  IElementType RPAREN = new CupTokenType(")");
  IElementType SCAN = new CupTokenType("scan");
  IElementType SEMICOLON = new CupTokenType(";");
  IElementType START = new CupTokenType("start");
  IElementType TERMINAL_ = new CupTokenType("terminal");
  IElementType WHITE_SPACE = new CupTokenType("whitespace");
  IElementType WITH = new CupTokenType("with");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
      if (type == ACTION_CODE_PART) {
        return new CupActionCodePartImpl(node);
      }
      else if (type == CLASS_NAME) {
        return new CupClassNameImpl(node);
      }
      else if (type == CODE_STRING_BLOCK) {
        return new CupCodeStringBlockImpl(node);
      }
      else if (type == DECLARED_NON_TERMINAL) {
        return new CupDeclaredNonTerminalImpl(node);
      }
      else if (type == DECLARED_TERMINAL) {
        return new CupDeclaredTerminalImpl(node);
      }
      else if (type == IMPORT_NAME) {
        return new CupImportNameImpl(node);
      }
      else if (type == IMPORT_STATEMENT) {
        return new CupImportStatementImpl(node);
      }
      else if (type == INIT_CODE_PART) {
        return new CupInitCodePartImpl(node);
      }
      else if (type == LABEL) {
        return new CupLabelImpl(node);
      }
      else if (type == NON_TERMINAL_DECLARATION) {
        return new CupNonTerminalDeclarationImpl(node);
      }
      else if (type == OPTIONAL_SEMICOLON) {
        return new CupOptionalSemicolonImpl(node);
      }
      else if (type == PACKAGE_NAME) {
        return new CupPackageNameImpl(node);
      }
      else if (type == PACKAGE_SPEC) {
        return new CupPackageSpecImpl(node);
      }
      else if (type == PARSER_CODE_PART) {
        return new CupParserCodePartImpl(node);
      }
      else if (type == PRECEDENCE_CLAUSE) {
        return new CupPrecedenceClauseImpl(node);
      }
      else if (type == PRECEDENCE_DECLARATION) {
        return new CupPrecedenceDeclarationImpl(node);
      }
      else if (type == PRECEDENCE_SYMBOL) {
        return new CupPrecedenceSymbolImpl(node);
      }
      else if (type == PRODUCTION) {
        return new CupProductionImpl(node);
      }
      else if (type == RIGHT_HAND_SIDE) {
        return new CupRightHandSideImpl(node);
      }
      else if (type == SCAN_CODE_PART) {
        return new CupScanCodePartImpl(node);
      }
      else if (type == START_DECLARATION) {
        return new CupStartDeclarationImpl(node);
      }
      else if (type == SYMBOL) {
        return new CupSymbolImpl(node);
      }
      else if (type == TERMINAL_DECLARATION) {
        return new CupTerminalDeclarationImpl(node);
      }
      else if (type == TYPE_NAME) {
        return new CupTypeNameImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}
