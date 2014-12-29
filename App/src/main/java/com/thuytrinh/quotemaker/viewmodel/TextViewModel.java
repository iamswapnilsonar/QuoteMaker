package com.thuytrinh.quotemaker.viewmodel;

import android.content.Context;
import android.graphics.Typeface;

import rx.Observable;
import rx.functions.Func1;

public class TextViewModel {
  public final ObservableProperty<CharSequence> text = new ObservableProperty<>();
  public final ObservableProperty<String> fontPath = new ObservableProperty<>();
  public final ObservableProperty<Float> x = new ObservableProperty<>(0f);
  public final ObservableProperty<Float> y = new ObservableProperty<>(0f);

  public TextViewModel() {
    // fontPath.setValue("fonts/BEBAS___.ttf");
    // fontPath.setValue("fonts/League Gothic.otf");
    fontPath.setValue("fonts/Anton.ttf");
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