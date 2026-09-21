package com.anaptecs.jeaf.validation.api.test;

import com.anaptecs.jeaf.validation.api.EnumSubset;

public class EnumClass {
  @EnumSubset(notAllowedLiterals = { "RED", "GREEN" })
  public Color firstColor;

  @EnumSubset(allowedLiterals = { "WHITE", "BLUE" })
  public Color secondColor;
}
