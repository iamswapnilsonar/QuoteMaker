package com.thuytrinh.quotemaker.viewmodel;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;

import com.thuytrinh.quotemaker.model.TextModel;
import com.thuytrinh.quotemaker.viewmodel.rx.ObservableAction;
import com.thuytrinh.quotemaker.viewmodel.rx.ObservableProperty;

import rx.Observable;
import rx.functions.Func1;

public class TextItem {
  private final ObservableProperty<String> text = new ObservableProperty<>();
  private final ObservableProperty<String> fontPath = new ObservableProperty<>("fonts/Anton.ttf");
  private final ObservableProperty<Float> size = new ObservableProperty<>(45f); /* In sp */
  private final ObservableProperty<Float> x = new ObservableProperty<>(0f);
  private final ObservableProperty<Float> y = new ObservableProperty<>(0f);
  private final ObservableProperty<Integer> gravity = new ObservableProperty<>(Gravity.CENTER);
  private final ObservableAction<Object> delete = new ObservableAction<>();

  public TextItem() {}

  public TextItem(TextModel model) {
    this();

    /*
    this.text.setValue(text);
    this.fontPath.setValue(fontPath);
    this.size.setValue(size);
    this.x.setValue(x);
    this.y.setValue(y);
    this.gravity.setValue(gravity);
    */
  }

  public ObservableProperty<String> text() {
    return text;
  }

  public ObservableProperty<Float> size() {
    return size;
  }

  public ObservableProperty<String> fontPath() {
    return fontPath;
  }

  public ObservableProperty<Float> x() {
    return x;
  }

  public ObservableProperty<Float> y() {
    return y;
  }

  public ObservableProperty<Integer> gravity() {
    return gravity;
  }

  public ObservableAction<Object> delete() {
    return delete;
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