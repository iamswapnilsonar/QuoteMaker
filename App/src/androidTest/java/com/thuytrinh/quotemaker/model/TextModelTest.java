package com.thuytrinh.quotemaker.model;

import android.test.AndroidTestCase;

import io.realm.Realm;
import io.realm.RealmResults;

import static org.assertj.core.api.Assertions.assertThat;

public class TextModelTest extends AndroidTestCase {
  public void testShouldPersistTextModel() {
    String databaseFileName = "TextModelTest_" + System.currentTimeMillis();
    Realm realm = Realm.getInstance(getContext(), databaseFileName);

    realm.beginTransaction();

    TextModel expected = realm.createObject(TextModel.class);
    expected.setText("Awesome!");
    expected.setFontPath("/awesome_font.ttf");
    expected.setSize(47f);
    expected.setX(4f);
    expected.setY(7f);
    expected.setGravity(74);

    realm.commitTransaction();

    RealmResults<TextModel> results = realm.where(TextModel.class).findAll();
    assertThat(results).hasSize(1);

    TextModel actual = results.first();
    assertThat(actual.getText()).isEqualTo(expected.getText());
    assertThat(actual.getFontPath()).isEqualTo(expected.getFontPath());
    assertThat(actual.getSize()).isEqualTo(expected.getSize());
    assertThat(actual.getX()).isEqualTo(expected.getX());
    assertThat(actual.getY()).isEqualTo(expected.getY());
    assertThat(actual.getGravity()).isEqualTo(expected.getGravity());

    Realm.deleteRealmFile(getContext(), databaseFileName);
  }
}