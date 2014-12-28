package com.thuytrinh.quotemaker.viewmodel;

import android.content.Context;

import com.thuytrinh.quotemaker.ObservableList;

import java.util.ArrayList;

import javax.inject.Inject;

public class CanvasViewModel {
  public final ObservableList<TextViewModel> items = new ObservableList<>(new ArrayList<TextViewModel>());
  private final Context context;

  @Inject
  public CanvasViewModel(Context context) {
    this.context = context;
  }
}