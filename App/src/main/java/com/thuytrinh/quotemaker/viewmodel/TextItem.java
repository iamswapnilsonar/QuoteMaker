package com.thuytrinh.quotemaker.viewmodel;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;

import com.thuytrinh.quotemaker.viewmodel.rx.ObservableAction;
import com.thuytrinh.quotemaker.viewmodel.rx.ObservableProperty;

import rx.Observable;
import rx.functions.Func1;

public class TextItem {
  public static final DbTable TABLE = new DbTable("TextItem", new DbField[] {
      Fields.ID,
      Fields.TEXT,
      Fields.FONT_PATH,
      Fields.SIZE,
      Fields.X,
      Fields.Y,
      Fields.GRAVITY
  });

  private final ObservableProperty<String> text = new ObservableProperty<>();
  private final ObservableProperty<String> fontPath = new ObservableProperty<>("fonts/Anton.ttf");
  private final ObservableProperty<Float> size = new ObservableProperty<>(45f);
  private final ObservableProperty<Float> x = new ObservableProperty<>(0f);
  private final ObservableProperty<Float> y = new ObservableProperty<>(0f);
  private final ObservableProperty<Integer> gravity = new ObservableProperty<>(Gravity.CENTER);
  private final ObservableAction<Object> delete = new ObservableAction<>();

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
    return fontPath().observe()
        .map(new Func1<String, Typeface>() {
          @Override
          public Typeface call(String value) {
            return Typeface.createFromAsset(appContext.getAssets(), value);
          }
        });
  }

  public ContentValues toValues(long quoteId) {
    ContentValues values = new ContentValues();
    values.put(Fields.TEXT.name, text.getValue());
    values.put(Fields.FONT_PATH.name, fontPath.getValue());
    values.put(Fields.SIZE.name, size.getValue());
    values.put(Fields.X.name, x.getValue());
    values.put(Fields.Y.name, y.getValue());
    values.put(Fields.GRAVITY.name, gravity.getValue());
    values.put(Fields.QUOTE_ID.name, quoteId);
    return values;
  }

  public static class Fields {
    public static final DbField ID = new DbField("_id", "INTEGER", "PRIMARY KEY AUTOINCREMENT");
    public static final DbField TEXT = new DbField("text", "TEXT");
    public static final DbField FONT_PATH = new DbField("fontPath", "TEXT");
    public static final DbField SIZE = new DbField("size", "REAL");
    public static final DbField X = new DbField("x", "REAL");
    public static final DbField Y = new DbField("y", "REAL");
    public static final DbField GRAVITY = new DbField("gravity", "INTEGER");
    public static final DbField QUOTE_ID = new DbField(
        "quoteId",
        "INTEGER",
        "REFERENCES " + Quote.TABLE.name + "(" + Quote.Fields.ID + ")"
    );
  }
}