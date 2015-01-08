package com.thuytrinh.quotemaker.viewmodel;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import static org.assertj.core.api.Assertions.assertThat;

public class DbHelperTest extends AndroidTestCase {
  public void testShouldCreateQuoteTable() {
    String dbName = "DbHelperTest" + System.currentTimeMillis();
    DbHelper dbHelper = new DbHelper(getContext(), dbName, 1);
    SQLiteDatabase db = dbHelper.getReadableDatabase();

    Cursor cursor = db.rawQuery("SELECT * FROM " + Quote.TABLE.name, null);
    assertThat(cursor.getColumnNames())
        .isNotEmpty()
        .containsExactly(Quote.TABLE.getFieldNames());

    cursor.close();
    dbHelper.close();
    assertTrue(getContext().getDatabasePath(dbName).delete());
  }
}