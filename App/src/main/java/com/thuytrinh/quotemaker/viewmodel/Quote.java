package com.thuytrinh.quotemaker.viewmodel;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.squareup.otto.Subscribe;
import com.thuytrinh.quotemaker.viewmodel.rx.ObservableList;
import com.thuytrinh.quotemaker.viewmodel.rx.ObservableProperty;

import java.util.ArrayList;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Action1;

public class Quote {
  public static final DbTable TABLE = new DbTable("Quote", new DbField[] {
      Fields.ID,
      Fields.BACKGROUND_COLOR
  });

  public final ObservableProperty<Long> id = new ObservableProperty<>();
  public final ObservableList<TextItem> items = new ObservableList<>(new ArrayList<TextItem>());
  public final ObservableProperty<Integer> backgroundColor = new ObservableProperty<>(0xff018db1);

  @Inject
  public Quote() {
    Observable.from(items)
        .subscribe(new Action1<TextItem>() {
          @Override
          public void call(final TextItem item) {
            item.delete().observe()
                .subscribe(new Action1<Object>() {
                  @Override
                  public void call(Object unused) {
                    items.remove(item);
                  }
                });
          }
        });
  }

  @Subscribe
  public void onEvent(Theme selectedTheme) {
    backgroundColor.setValue(selectedTheme.getBackgroundColor());
  }

  // TODO: Should be observable.
  public void save(DatabaseHelper databaseHelper) {
    SQLiteDatabase database = databaseHelper.getWritableDatabase();

    ContentValues values = new ContentValues();
    values.put(Fields.BACKGROUND_COLOR.name, backgroundColor.getValue());

    if (!id.hasValue()) {
      long newlyInsertedId = database.insertOrThrow(TABLE.name, null, values);
      id.setValue(newlyInsertedId);
    } else {
      database.update(TABLE.name, values, "_id = ?", new String[] {id.getValue().toString()});
    }
  }

  public static class Fields {
    public static final DbField ID = new DbField("_id", "INTEGER", "PRIMARY KEY AUTOINCREMENT");
    public static final DbField BACKGROUND_COLOR = new DbField("backgroundColor", "INTEGER");
  }
}