package com.thuytrinh.quotemaker.viewmodel;

import android.graphics.Typeface;

public class FontViewModel {
  public final ObservableProperty<Boolean> isSelected = new ObservableProperty<>(false);
  public final String preview;
  public final String fontPath;
  public final Typeface typeface;

  public FontViewModel(String preview, String fontPath, Typeface typeface) {
    this.preview = preview;
    this.fontPath = fontPath;
    this.typeface = typeface;
  }
}