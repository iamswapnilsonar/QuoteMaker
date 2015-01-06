package com.thuytrinh.quotemaker.viewmodel;

import com.thuytrinh.quotemaker.viewmodel.rx.ObservableProperty;

public class ThemeViewModel {
  private final String name;
  private final int backgroundColor;
  private final int foregroundColor;
  private final ObservableProperty<Boolean> isSelected = new ObservableProperty<>(false);

  public ThemeViewModel(String name, int backgroundColor, int foregroundColor) {
    this.name = name;
    this.backgroundColor = backgroundColor;
    this.foregroundColor = foregroundColor;
  }

  public int getBackgroundColor() {
    return backgroundColor;
  }

  public String getName() {
    return name;
  }

  public int getForegroundColor() {
    return foregroundColor;
  }

  public ObservableProperty<Boolean> isSelected() {
    return isSelected;
  }

  public void toggle() {
    isSelected.setValue(!isSelected.getValue());
  }
}