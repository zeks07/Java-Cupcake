// This is a generated file. Not intended for manual editing.
package com.zeks.javacupcake.lang.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static com.zeks.javacupcake.lang.psi.CupTypes.*;
import com.zeks.javacupcake.lang.psi.CupProductionLine;
import com.zeks.javacupcake.lang.psi.*;

public class CupProductionImpl extends CupProductionLine implements CupProduction {

  public CupProductionImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull CupVisitor visitor) {
    visitor.visitProduction(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof CupVisitor) accept((CupVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<CupRightHandSide> getRightHandSideList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, CupRightHandSide.class);
  }

  @Override
  @NotNull
  public CupSymbol getSymbol() {
    return findNotNullChildByClass(CupSymbol.class);
  }

}
