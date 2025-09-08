// This is a generated file. Not intended for manual editing.
package com.zeks.javacupcake.lang.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static com.zeks.javacupcake.lang.psi.CupTypes.*;
import com.zeks.javacupcake.lang.psi.CupTerminalDeclarationLine;
import com.zeks.javacupcake.lang.psi.*;

public class CupTerminalDeclarationImpl extends CupTerminalDeclarationLine implements CupTerminalDeclaration {

  public CupTerminalDeclarationImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull CupVisitor visitor) {
    visitor.visitTerminalDeclaration(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof CupVisitor) accept((CupVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<CupDeclaredTerminal> getDeclaredTerminalList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, CupDeclaredTerminal.class);
  }

  @Override
  @Nullable
  public CupTypeName getTypeName() {
    return findChildByClass(CupTypeName.class);
  }

}
