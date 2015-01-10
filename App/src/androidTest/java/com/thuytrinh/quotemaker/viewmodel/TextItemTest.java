package com.thuytrinh.quotemaker.viewmodel;

import android.content.ContentValues;
import android.view.Gravity;

import junit.framework.TestCase;

import static com.thuytrinh.quotemaker.viewmodel.TextItem.Fields.FONT_PATH;
import static com.thuytrinh.quotemaker.viewmodel.TextItem.Fields.GRAVITY;
import static com.thuytrinh.quotemaker.viewmodel.TextItem.Fields.QUOTE_ID;
import static com.thuytrinh.quotemaker.viewmodel.TextItem.Fields.SIZE;
import static com.thuytrinh.quotemaker.viewmodel.TextItem.Fields.TEXT;
import static com.thuytrinh.quotemaker.viewmodel.TextItem.Fields.X;
import static com.thuytrinh.quotemaker.viewmodel.TextItem.Fields.Y;
import static org.assertj.core.api.Assertions.assertThat;

public class TextItemTest extends TestCase {
  public void testToValues() throws Exception {
    TextItem item = new TextItem();
    item.text().setValue("Awesome!");
    item.fontPath().setValue("/AwesomeFont.ttf");
    item.size().setValue(47f);
    item.x().setValue(4f);
    item.y().setValue(7f);
    item.gravity().setValue(Gravity.RIGHT);

    long quoteId = 47L;
    ContentValues values = item.toValues(quoteId);
    assertThat(values.getAsString(TEXT.name)).isEqualTo(item.text().getValue());
    assertThat(values.getAsString(FONT_PATH.name)).isEqualTo(item.fontPath().getValue());
    assertThat(values.getAsFloat(SIZE.name)).isEqualTo(item.size().getValue());
    assertThat(values.getAsFloat(X.name)).isEqualTo(item.x().getValue());
    assertThat(values.getAsFloat(Y.name)).isEqualTo(item.y().getValue());
    assertThat(values.getAsInteger(GRAVITY.name)).isEqualTo(item.gravity().getValue());
    assertThat(values.getAsLong(QUOTE_ID.name)).isEqualTo(quoteId);
  }
}