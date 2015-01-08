package com.thuytrinh.quotemaker.viewmodel;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;

import com.google.gson.JsonObject;
import com.thuytrinh.quotemaker.viewmodel.rx.ObservableAction;
import com.thuytrinh.quotemaker.viewmodel.rx.ObservableProperty;

import rx.Observable;
import rx.functions.Func1;

public class TextItem {
  public final ObservableProperty<CharSequence> text = new ObservableProperty<>();
  public final ObservableProperty<String> fontPath = new ObservableProperty<>("fonts/Anton.ttf");
  public final ObservableProperty<Float> x = new ObservableProperty<>(0f);
  public final ObservableProperty<Float> y = new ObservableProperty<>(0f);
  public final ObservableProperty<Integer> gravity = new ObservableProperty<>(Gravity.CENTER);
  public final ObservableProperty<Float> size = new ObservableProperty<>(45f);
  public final ObservableAction<Object> delete = new ObservableAction<>();

  public Observable<Typeface> getTypeface(final Context appContext) {
    return fontPath.observe()
        .map(new Func1<String, Typeface>() {
          @Override
          public Typeface call(String value) {
            return Typeface.createFromAsset(appContext.getAssets(), value);
          }
        });
  }

  public JsonObject toJson() {
    JsonObject jsonItem = new JsonObject();
    jsonItem.addProperty(Fields.TEXT, String.valueOf(text.getValue()));
    jsonItem.addProperty(Fields.FONT_PATH, fontPath.getValue());
    jsonItem.addProperty(Fields.X, x.getValue());
    jsonItem.addProperty(Fields.Y, y.getValue());
    jsonItem.addProperty(Fields.GRAVITY, gravity.getValue());
    jsonItem.addProperty(Fields.SIZE, size.getValue());
    return jsonItem;
  }

  public static class Fields {
    public static final String TEXT = "text";
    public static final String FONT_PATH = "fontPath";
    public static final String X = "x";
    public static final String Y = "y";
    public static final String GRAVITY = "gravity";
    public static final String SIZE = "size";
  }
}