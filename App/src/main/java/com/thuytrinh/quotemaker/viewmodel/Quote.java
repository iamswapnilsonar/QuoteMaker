package com.thuytrinh.quotemaker.viewmodel;

import android.content.ContentValues;
import android.database.Cursor;
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

  private final ObservableProperty<Long> id = new ObservableProperty<>();
  private final ObservableList<TextItem> items = new ObservableList<>(new ArrayList<TextItem>());
  private final ObservableProperty<Integer> backgroundColor = new ObservableProperty<>(0xff018db1);

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

  public Quote(Cursor cursor) {
    this();

    long id = cursor.getLong(cursor.getColumnIndex(Fields.ID.name));
    int backgroundColor = cursor.getInt(cursor.getColumnIndex(Fields.BACKGROUND_COLOR.name));

    this.id.setValue(id);
    this.backgroundColor.setValue(backgroundColor);
  }

  public ObservableProperty<Long> id() {
    return id;
  }

  public ObservableList<TextItem> items() {
    return items;
  }

  public ObservableProperty<Integer> backgroundColor() {
    return backgroundColor;
  }

  @Subscribe
  public void onEvent(Theme selectedTheme) {
    backgroundColor.setValue(selectedTheme.getBackgroundColor());
  }

  // TODO: Should be observable.
  public void save(DatabaseHelper databaseHelper) {
    SQLiteDatabase database = databaseHelper.getWritableDatabase();
    database.beginTransaction();

    // Save the quote.
    ContentValues quoteValues = toValues();
    if (!id.hasValue()) {
      long newlyInsertedId = database.insertOrThrow(TABLE.name, null, quoteValues);
      id.setValue(newlyInsertedId);
    } else {
      database.update(TABLE.name, quoteValues, "_id = ?", new String[] {id.getValue().toString()});
    }

    // And save its items.
    saveItems(database);

    // Done!
    database.setTransactionSuccessful();
    database.endTransaction();
  }

  public ContentValues toValues() {
    ContentValues values = new ContentValues();
    values.put(Fields.BACKGROUND_COLOR.name, backgroundColor.getValue());
    return values;
  }

  // TODO: Should be observable.
  public void loadItems(DatabaseHelper databaseHelper) {
    // Remember to clear all previous items.
    items.clear();

    SQLiteDatabase database = databaseHelper.getReadableDatabase();
    Cursor cursor = database.query(
        TextItem.TABLE.name, null,
        TextItem.Fields.QUOTE_ID + " = ?",
        new String[] {id.getValue().toString()},
        null, null, null
    );
    try {
      while (cursor.moveToNext()) {
        TextItem item = new TextItem(cursor);
        items.add(item);
      }
    } finally {
      if (!cursor.isClosed()) {
        cursor.close();
      }
    }
  }

  private void saveItems(SQLiteDatabase database) {
    // Delete old items first.
    database.delete(
        TextItem.TABLE.name,
        TextItem.Fields.QUOTE_ID.name + " = ?",
        new String[] {id.getValue().toString()}
    );

    // Save new items.
    for (TextItem item : items) {
      ContentValues itemValues = item.toValues(id.getValue());
      database.insertOrThrow(TextItem.TABLE.name, null, itemValues);
    }
  }

  public static class Fields {
    public static final DbField ID = new DbField("_id", "INTEGER", "PRIMARY KEY AUTOINCREMENT");
    public static final DbField BACKGROUND_COLOR = new DbField("backgroundColor", "INTEGER");
  }
}