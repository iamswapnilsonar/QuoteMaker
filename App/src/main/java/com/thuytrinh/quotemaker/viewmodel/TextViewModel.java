package com.thuytrinh.quotemaker.viewmodel;

import android.content.Context;
import android.graphics.Typeface;

import rx.Observable;
import rx.functions.Func1;

public class TextViewModel {
  public final ObservableProperty<CharSequence> text = new ObservableProperty<>();
  public final ObservableProperty<String> fontPath = new ObservableProperty<>();

  public TextViewModel() {
    fontPath.setValue("fonts/BEBAS___.ttf");
  }

  public Observable<Typeface> getTypeface(final Context appContext) {
    return fontPath.observe()
        .map(new Func1<String, Typeface>() {
          @Override
          public Typeface call(String value) {
            return Typeface.createFromAsset(appContext.getAssets(), value);
          }
        });
  }
}