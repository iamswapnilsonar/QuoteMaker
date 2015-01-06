package com.thuytrinh.quotemaker.viewmodel;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;

import com.thuytrinh.quotemaker.viewmodel.rx.ObservableAction;
import com.thuytrinh.quotemaker.viewmodel.rx.ObservableProperty;

import rx.Observable;
import rx.functions.Func1;

public class TextItem {
  public final ObservableProperty<CharSequence> text = new ObservableProperty<>();
  public final ObservableProperty<String> fontPath = new ObservableProperty<>();
  public final ObservableProperty<Float> x = new ObservableProperty<>(0f);
  public final ObservableProperty<Float> y = new ObservableProperty<>(0f);
  public final ObservableProperty<Integer> gravity = new ObservableProperty<>(Gravity.RIGHT);
  public final ObservableProperty<Float> size = new ObservableProperty<>(45f);
  public final ObservableAction<Object> delete = new ObservableAction<>();

  public TextItem() {
    // Default font.
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