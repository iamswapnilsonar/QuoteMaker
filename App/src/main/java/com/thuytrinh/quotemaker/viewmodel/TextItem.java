package com.thuytrinh.quotemaker.viewmodel;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;

import com.thuytrinh.quotemaker.viewmodel.rx.ObservableAction;
import com.thuytrinh.quotemaker.viewmodel.rx.ObservableProperty;

import rx.Observable;
import rx.functions.Func1;

public class TextItem {
  public static final DbTable TABLE = new DbTable("TextItems", new DbField[] {
      Fields.ID,
      Fields.TEXT,
      Fields.FONT_PATH,
      Fields.X,
      Fields.Y,
      Fields.GRAVITY,
      Fields.SIZE,
      Fields.QUOTE_ID
  });

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

  public static class Fields {
    public static final DbField ID = new DbField("_id", "INTEGER", "PRIMARY KEY AUTOINCREMENT");
    public static final DbField TEXT = new DbField("text", "TEXT");
    public static final DbField FONT_PATH = new DbField("font_path", "TEXT");
    public static final DbField X = new DbField("x", "REAL");
    public static final DbField Y = new DbField("y", "REAL");
    public static final DbField GRAVITY = new DbField("gravity", "INTEGER");
    public static final DbField SIZE = new DbField("size", "REAL");
    public static final DbField QUOTE_ID = new DbField(
        "quote_id",
        "INTEGER",
        "REFERENCES " + Quote.TABLE.name + "(" + Quote.Fields.ID + ")"
    );
  }
}