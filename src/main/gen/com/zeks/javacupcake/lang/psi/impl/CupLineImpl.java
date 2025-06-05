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

public class CupLineImpl extends ASTWrapperPsiElement implements CupLine {

  public CupLineImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull CupVisitor visitor) {
    visitor.visitLine(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof CupVisitor) accept((CupVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public CupCodeParts getCodeParts() {
    return findChildByClass(CupCodeParts.class);
  }

  @Override
  @Nullable
  public CupImportStatement getImportStatement() {
    return findChildByClass(CupImportStatement.class);
  }

  @Override
  @Nullable
  public CupPackageSpec getPackageSpec() {
    return findChildByClass(CupPackageSpec.class);
  }

  @Override
  @Nullable
  public CupPrecedenceDeclaration getPrecedenceDeclaration() {
    return findChildByClass(CupPrecedenceDeclaration.class);
  }

  @Override
  @Nullable
  public CupProduction getProduction() {
    return findChildByClass(CupProduction.class);
  }

  @Override
  @Nullable
  public CupStartSpec getStartSpec() {
    return findChildByClass(CupStartSpec.class);
  }

  @Override
  @Nullable
  public CupSymbolDeclaration getSymbolDeclaration() {
    return findChildByClass(CupSymbolDeclaration.class);
  }

}
