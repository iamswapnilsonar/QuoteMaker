package com.thuytrinh.quotemaker.viewmodel;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
  public DatabaseHelper(Context context, String name, int version) {
    super(context, name, null, version);
  }

  @Override
  public void onCreate(SQLiteDatabase database) {
    database.execSQL(Quote.TABLE.getCreateSql());
  }

  @Override
  public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {}
}