// This is a generated file. Not intended for manual editing.
package com.zeks.javacupcake.lang.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface CupLine extends PsiElement {

  @Nullable
  CupCodeParts getCodeParts();

  @Nullable
  CupImportStatement getImportStatement();

  @Nullable
  CupPackageSpec getPackageSpec();

  @Nullable
  CupPrecedenceDeclaration getPrecedenceDeclaration();

  @Nullable
  CupProduction getProduction();

  @Nullable
  CupStartSpec getStartSpec();

  @Nullable
  CupSymbolDeclaration getSymbolDeclaration();

}
