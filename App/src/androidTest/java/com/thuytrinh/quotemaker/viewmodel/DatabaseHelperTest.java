package com.thuytrinh.quotemaker.viewmodel;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import static org.assertj.core.api.Assertions.assertThat;

public class DatabaseHelperTest extends AndroidTestCase {
  public void testShouldCreateQuoteTable() {
    String databaseName = "DatabaseHelperTest_" + System.currentTimeMillis();
    DatabaseHelper databaseHelper = new DatabaseHelper(getContext(), databaseName, 1);
    SQLiteDatabase db = databaseHelper.getReadableDatabase();

    Cursor cursor = db.rawQuery("SELECT * FROM " + Quote.TABLE.name, null);
    assertThat(cursor.getColumnNames())
        .isNotEmpty()
        .containsExactly(Quote.TABLE.getFieldNames());

    cursor.close();
    databaseHelper.close();
    assertTrue(getContext().getDatabasePath(databaseName).delete());
  }
}