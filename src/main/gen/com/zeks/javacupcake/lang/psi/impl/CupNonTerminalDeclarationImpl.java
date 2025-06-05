// This is a generated file. Not intended for manual editing.
package com.zeks.javacupcake.lang.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static com.zeks.javacupcake.lang.psi.CupTypes.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.zeks.javacupcake.lang.psi.*;

public class CupNonTerminalDeclarationImpl extends ASTWrapperPsiElement implements CupNonTerminalDeclaration {

  public CupNonTerminalDeclarationImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull CupVisitor visitor) {
    visitor.visitNonTerminalDeclaration(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof CupVisitor) accept((CupVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<CupDeclaredNonTerminal> getDeclaredNonTerminalList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, CupDeclaredNonTerminal.class);
  }

  @Override
  @Nullable
  public CupTypeName getTypeName() {
    return findChildByClass(CupTypeName.class);
  }

}
