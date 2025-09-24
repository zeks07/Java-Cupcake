// This is a generated file. Not intended for manual editing.
package com.zeks.javacupcake.lang.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface CupNonTerminalDeclaration extends PsiElement {

  @NotNull
  List<CupDeclaredNonTerminal> getDeclaredNonTerminalList();

  @Nullable
  CupNonTerminalAlternative getNonTerminalAlternative();

  @Nullable
  CupTypeName getTypeName();

}
