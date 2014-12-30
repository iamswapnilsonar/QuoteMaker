package com.thuytrinh.quotemaker.viewmodel;

import android.content.Context;

import com.squareup.otto.Bus;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

public class FontPicker {
  public final List<String> fontPaths;
  public final Bus eventBus;
  private final Context appContext;

  @Inject
  public FontPicker(Context appContext, Bus eventBus) {
    this.appContext = appContext;
    this.eventBus = eventBus;

    fontPaths = Arrays.asList(
        "fonts/League Gothic.otf",
        "fonts/BEBAS___.ttf",
        "fonts/Anton.ttf",
        "fonts/pacifico.ttf",
        "fonts/Raleway-ExtraLight.ttf",
        "fonts/MrDeHaviland-Regular.ttf"
    );
  }
}