package com.thuytrinh.quotemaker.viewmodel;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import com.thuytrinh.quotemaker.R;

import static org.assertj.core.api.Assertions.assertThat;

public class QuoteTest extends AndroidTestCase {
  public void testShouldCreateNewQuoteOnDisk() {
    String databaseName = "QuoteTest_" + System.currentTimeMillis();
    DatabaseHelper databaseHelper = new DatabaseHelper(getContext(), databaseName, 1);

    Quote quote = new Quote();
    assertThat(quote.id.hasValue()).isFalse();

    int raspberry = getContext().getResources().getColor(R.color.raspberry);
    quote.backgroundColor.setValue(raspberry);
    quote.save(databaseHelper);

    // Id should be updated then.
    assertThat(quote.id.hasValue()).isTrue();
    assertThat(quote.id.getValue()).isGreaterThan(0L);

    // There should be a newly inserted row in database.
    SQLiteDatabase database = databaseHelper.getReadableDatabase();
    Cursor cursor = database.rawQuery("SELECT * FROM " + Quote.TABLE.name, null);
    assertThat(cursor).isNotNull();
    assertThat(cursor.getCount()).isEqualTo(1);

    // Make sure we persisted proper data.
    cursor.moveToFirst();
    assertThat(cursor.getLong(cursor.getColumnIndex(Quote.Fields.ID.name)))
        .isEqualTo(quote.id.getValue());
    assertThat(cursor.getInt(cursor.getColumnIndex(Quote.Fields.BACKGROUND_COLOR.name)))
        .isEqualTo(quote.backgroundColor.getValue());

    // Done!
    cursor.close();
    databaseHelper.close();
    assertTrue(getContext().getDatabasePath(databaseName).delete());
  }
}