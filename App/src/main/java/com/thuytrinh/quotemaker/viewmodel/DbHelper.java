package com.thuytrinh.quotemaker.viewmodel;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
  public DbHelper(Context context, String name, int version) {
    super(context, name, null, version);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL(Quote.TABLE.getCreateSql());
    db.execSQL(TextItem.TABLE.getCreateSql());
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
}