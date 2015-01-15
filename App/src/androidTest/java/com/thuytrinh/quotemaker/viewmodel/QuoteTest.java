package com.thuytrinh.quotemaker.viewmodel;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.test.AndroidTestCase;

import java.io.File;
import java.util.Comparator;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import rx.functions.Action1;

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

    quoteCursor.moveToFirst();

    Quote actualQuote = new Quote(quoteCursor);
    assertThat(actualQuote.id().getValue())
        .isEqualTo(expectedQuote.id().getValue());
    assertThat(actualQuote.backgroundColor().getValue())
        .isEqualTo(expectedQuote.backgroundColor().getValue());
    assertThat(actualQuote.snapshotFile().getValue())
        .isEqualTo(expectedQuote.snapshotFile().getValue());

    actualQuote.loadItems(databaseHelper);
    assertThat(actualQuote.items())
        .isNotEmpty()
        .hasSize(expectedQuote.items().size())
        .usingElementComparator(new Comparator<TextItem>() {
          @Override
          public int compare(TextItem lhs, TextItem rhs) {
            return Objects.equals(lhs.text().getValue(), rhs.text().getValue()) &&
                Objects.equals(lhs.fontPath().getValue(), rhs.fontPath().getValue()) &&
                Objects.equals(lhs.size().getValue(), rhs.size().getValue()) &&
                Objects.equals(lhs.x().getValue(), rhs.x().getValue()) &&
                Objects.equals(lhs.y().getValue(), rhs.y().getValue()) &&
                Objects.equals(lhs.gravity().getValue(), rhs.gravity().getValue())
                ? 0 : 1;
          }
        })
        .containsExactly(item0, item1);

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

  public void testSaveSnapshot() {
    Quote quote = new Quote();
    quote.id().setValue(123L);

    final AtomicReference<File> snapshotFile = new AtomicReference<>();
    quote.saveSnapshot(
        Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888),
        getContext().getFilesDir()
    ).subscribe(new Action1<File>() {
      @Override
      public void call(File file) {
        snapshotFile.set(file);
      }
    });

    assertThat(snapshotFile.get()).isNotNull();
    assertThat(snapshotFile.get().getName()).isEqualTo("snapshot_123.png");
    assertThat(snapshotFile.get()).isEqualTo(quote.snapshotFile().getValue());
  }
}