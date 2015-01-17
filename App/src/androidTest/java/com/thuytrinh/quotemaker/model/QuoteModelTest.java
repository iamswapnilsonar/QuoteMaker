package com.thuytrinh.quotemaker.model;

import android.test.AndroidTestCase;

import io.realm.Realm;
import io.realm.RealmResults;

import static org.assertj.core.api.Assertions.assertThat;

public class QuoteModelTest extends AndroidTestCase {
  public void testShouldPersistQuoteModel() {
    String databaseFileName = "QuoteModelTest_" + System.currentTimeMillis();
    Realm realm = Realm.getInstance(getContext(), databaseFileName);

    realm.beginTransaction();

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
}