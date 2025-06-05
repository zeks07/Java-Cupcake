// This is a generated file. Not intended for manual editing.
package com.zeks.javacupcake.lang.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface CupRightHandSide extends PsiElement {

  @Nullable
  CupClassName getClassName();

  @NotNull
  List<CupCodeStringBlock> getCodeStringBlockList();

  @NotNull
  List<CupLabel> getLabelList();

  @Nullable
  CupPrecedenceClause getPrecedenceClause();

  @NotNull
  List<CupSymbol> getSymbolList();

}
