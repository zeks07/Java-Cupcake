// This is a generated file. Not intended for manual editing.
package com.zeks.javacupcake.lang.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface CupCodeParts extends PsiElement {

  @Nullable
  CupActionCodePart getActionCodePart();

  @Nullable
  CupCodeStringBlock getCodeStringBlock();

  @Nullable
  CupInitCodePart getInitCodePart();

  @Nullable
  CupOptionalSemicolon getOptionalSemicolon();

  @Nullable
  CupParserCodePart getParserCodePart();

  @Nullable
  CupScanCodePart getScanCodePart();

}
