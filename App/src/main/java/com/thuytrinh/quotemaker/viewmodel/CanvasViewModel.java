package com.thuytrinh.quotemaker.viewmodel;

import android.content.Context;

import javax.inject.Inject;

public class CanvasViewModel {
  private final Context context;

  @Inject
  public CanvasViewModel(Context context) {
    this.context = context;
  }
}