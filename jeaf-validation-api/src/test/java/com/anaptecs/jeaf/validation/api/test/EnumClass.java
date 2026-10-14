package com.anaptecs.jeaf.validation.api.test;

import java.util.List;

import com.anaptecs.jeaf.validation.api.EnumSubset;

public class EnumClass {
  @EnumSubset(notAllowedLiterals = { "RED", "GREEN" })
  public Color firstColor;

  @EnumSubset(allowedLiterals = { "WHITE", "BLUE" })
  @EnumSubset(allowedLiterals = { "BLACK" }, groups = String.class)
  public Color secondColor;

  public List<@EnumSubset(allowedLiterals = { "RED", "GREEN", "BLUE" }) Color> myColors;
}
