package com.thuytrinh.quotemaker.viewmodel;

import android.graphics.Typeface;

import com.thuytrinh.quotemaker.viewmodel.rx.ObservableProperty;

public class FontItem {
  public final ObservableProperty<Boolean> isSelected = new ObservableProperty<>(false);
  public final String preview;
  public final String fontPath;
  public final Typeface typeface;

  public FontItem(String preview, String fontPath, Typeface typeface) {
    this.preview = preview;
    this.fontPath = fontPath;
    this.typeface = typeface;
  }
}