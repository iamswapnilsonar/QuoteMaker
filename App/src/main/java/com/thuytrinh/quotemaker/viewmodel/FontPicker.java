package com.thuytrinh.quotemaker.viewmodel;

import android.content.Context;
import android.graphics.Typeface;

import com.squareup.otto.Bus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import rx.functions.Action1;

public class FontPicker {
  public final List<FontItem> fonts = new ArrayList<>();
  private final Bus eventBus;
  private final Context appContext;

  @Inject
  public FontPicker(Context appContext, final Bus eventBus) {
    this.appContext = appContext;
    this.eventBus = eventBus;

    String[] previews = new String[] {
        "easier", "interesting",
        "honest", "forests",
        "Saturday", "dinner",
        "comfortable", "gently",
        "fresh", "pal",
        "warmth", "rest",
        "welcome", "dearest",
        "useful", "cherry",
        "safe", "better",
        "piano", "silk",
        "relief", "rhyme",
        "hi", "agree", "water"
    };

    List<String> fontPaths = Arrays.asList(
        "fonts/League Gothic.otf",
        "fonts/BEBAS___.ttf",
        "fonts/Anton.ttf",
        "fonts/pacifico.ttf",
        "fonts/Raleway-ExtraLight.ttf",
        "fonts/MrDeHaviland-Regular.ttf",
        "fonts/FingerPaint-Regular.ttf",
        "fonts/JuliusSansOne-Regular.ttf",
        "fonts/MrDafoe-Regular.ttf",
        "fonts/SpecialElite.ttf",
        "fonts/CabinSketch-Regular.ttf"
    );

    for (int i = 0, size = fontPaths.size(); i < size; i++) {
      String fontPath = fontPaths.get(i);
      Typeface typeface = Typeface.createFromAsset(appContext.getAssets(), fontPath);
      fonts.add(new FontItem(
          previews[i],
          fontPath,
          typeface
      ));
    }

    for (final FontItem font : fonts) {
      font.isSelected.observe().subscribe(new Action1<Boolean>() {
        @Override
        public void call(Boolean value) {
          if (value) {
            eventBus.post(font);
          }
        }
      });
    }
  }
}