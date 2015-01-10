package com.thuytrinh.quotemaker.viewmodel;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import static org.assertj.core.api.Assertions.assertThat;

public class QuoteTest extends AndroidTestCase {
  public void testShouldCreateNewQuoteOnDisk() {
    String databaseName = "QuoteTest_" + System.currentTimeMillis();
    DatabaseHelper databaseHelper = new DatabaseHelper(getContext(), databaseName, 1);

    Quote expectedQuote = new Quote();
    expectedQuote.backgroundColor().setValue(0xff00ff);

    TextItem item0 = new TextItem();
    item0.text().setValue("item0");
    expectedQuote.items().add(item0);

    TextItem item1 = new TextItem();
    item1.text().setValue("item1");
    expectedQuote.items().add(item1);

    assertThat(expectedQuote.id().hasValue()).isFalse();
    expectedQuote.save(databaseHelper);

    // Id should be updated then.
    assertThat(expectedQuote.id().hasValue()).isTrue();
    assertThat(expectedQuote.id().getValue()).isGreaterThan(0L);

    // There should be a newly inserted row in database.
    SQLiteDatabase database = databaseHelper.getReadableDatabase();
    Cursor quoteCursor = database.rawQuery("SELECT * FROM " + Quote.TABLE.name, null);
    assertThat(quoteCursor).isNotNull();
    assertThat(quoteCursor.getCount()).isEqualTo(1);

    Cursor itemCursor = database.rawQuery("SELECT * FROM " + TextItem.TABLE.name, null);
    assertThat(itemCursor).isNotNull();
    assertThat(itemCursor.getCount()).isEqualTo(2);

    // Make sure we persisted proper data.
    quoteCursor.moveToFirst();
    Quote actualQuote = new Quote(quoteCursor);
    assertThat(actualQuote.id().getValue())
        .isEqualTo(expectedQuote.id().getValue());
    assertThat(actualQuote.backgroundColor().getValue())
        .isEqualTo(expectedQuote.backgroundColor().getValue());

    actualQuote.loadItems(databaseHelper);
    assertThat(actualQuote.items())
        .isNotEmpty()
        .hasSize(expectedQuote.items().size());

    // Done!
    quoteCursor.close();
    databaseHelper.close();
    assertTrue(getContext().getDatabasePath(databaseName).delete());
  }

  public void testToValues() {
    Quote quote = new Quote();
    quote.backgroundColor().setValue(0xff00ff);
    ContentValues values = quote.toValues();

    assertThat(values.getAsInteger(Quote.Fields.BACKGROUND_COLOR.name))
        .isEqualTo(quote.backgroundColor().getValue());
  }
}