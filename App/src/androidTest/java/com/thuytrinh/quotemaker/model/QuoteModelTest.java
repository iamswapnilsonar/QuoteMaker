package com.thuytrinh.quotemaker.model;

import android.test.AndroidTestCase;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.internal.Table;

import static org.assertj.core.api.Assertions.assertThat;

public class QuoteModelTest extends AndroidTestCase {
  public void testShouldPersistQuoteModel() {
    String databaseFileName = "QuoteModelTest_" + System.currentTimeMillis();
    Realm realm = Realm.getInstance(getContext(), databaseFileName);

    realm.beginTransaction();

    Table table = realm.getTable(QuoteModel.class);
    assertThat(table.getName()).isEqualTo("class_QuoteModel");
    assertThat(table.getColumnCount()).isEqualTo(2);
    assertThat(table.getColumnName(table.getColumnIndex("backgroundColor"))).isEqualTo("backgroundColor");
    assertThat(table.getColumnName(table.getColumnIndex("snapshotFilePath"))).isEqualTo("snapshotFilePath");

    QuoteModel expected = realm.createObject(QuoteModel.class);
    expected.setBackgroundColor(123456);
    expected.setSnapshotFilePath("/awesome_quote.ttf");

    realm.commitTransaction();

    RealmResults<QuoteModel> results = realm.where(QuoteModel.class).findAll();
    assertThat(results).hasSize(1);

    QuoteModel actual = results.first();
    assertThat(actual.getBackgroundColor()).isEqualTo(expected.getBackgroundColor());
    assertThat(actual.getSnapshotFilePath()).isEqualTo(expected.getSnapshotFilePath());

    Realm.deleteRealmFile(getContext(), databaseFileName);
  }

  public void testShouldPersistTwoQuoteModels() {
    String databaseFileName = "QuoteModelTest_" + System.currentTimeMillis();
    Realm realm = Realm.getInstance(getContext(), databaseFileName);

    realm.beginTransaction();

    QuoteModel expected0 = realm.createObject(QuoteModel.class);
    expected0.setBackgroundColor(12);
    expected0.setSnapshotFilePath("/awesome_quote0.ttf");

    QuoteModel expected1 = realm.createObject(QuoteModel.class);
    expected1.setBackgroundColor(13);
    expected1.setSnapshotFilePath("/awesome_quote1.ttf");

    realm.commitTransaction();

    RealmResults<QuoteModel> results = realm.where(QuoteModel.class).findAll();
    assertThat(results).hasSize(2);

    QuoteModel actual0 = results.first();
    assertThat(actual0.getBackgroundColor()).isEqualTo(expected0.getBackgroundColor());
    assertThat(actual0.getSnapshotFilePath()).isEqualTo(expected0.getSnapshotFilePath());

    QuoteModel actual1 = results.last();
    assertThat(actual1.getBackgroundColor()).isEqualTo(expected1.getBackgroundColor());
    assertThat(actual1.getSnapshotFilePath()).isEqualTo(expected1.getSnapshotFilePath());

    Realm.deleteRealmFile(getContext(), databaseFileName);
  }
}