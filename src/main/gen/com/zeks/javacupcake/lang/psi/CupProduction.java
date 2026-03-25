// This is a generated file. Not intended for manual editing.
package com.zeks.javacupcake.lang.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import com.zeks.javacupcake.lang.psi.stubs.CupProductionStub;

public interface CupProduction extends PsiElement, StubBasedPsiElement<CupProductionStub> {

  @Nullable
  CupAssign getAssign();

  @NotNull
  List<CupRightHandSide> getRightHandSideList();

  @NotNull
  CupSymbol getSymbol();

}
