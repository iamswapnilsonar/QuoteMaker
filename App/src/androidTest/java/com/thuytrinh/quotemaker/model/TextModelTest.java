package com.thuytrinh.quotemaker.model;

import android.test.AndroidTestCase;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.internal.Table;

import static org.assertj.core.api.Assertions.assertThat;

public class TextModelTest extends AndroidTestCase {
  public void testShouldCreateTextModelTable() {
    String databaseFileName = "TextModelTest_" + System.currentTimeMillis();
    Realm realm = Realm.getInstance(getContext(), databaseFileName);

    Table table = realm.getTable(TextModel.class);
    assertThat(table.getName()).isEqualTo("class_TextModel");
    assertThat(table.getColumnCount()).isEqualTo(6);
    assertThat(table.getColumnName(table.getColumnIndex("text"))).isEqualTo("text");
    assertThat(table.getColumnName(table.getColumnIndex("fontPath"))).isEqualTo("fontPath");
    assertThat(table.getColumnName(table.getColumnIndex("size"))).isEqualTo("size");
    assertThat(table.getColumnName(table.getColumnIndex("x"))).isEqualTo("x");
    assertThat(table.getColumnName(table.getColumnIndex("y"))).isEqualTo("y");
    assertThat(table.getColumnName(table.getColumnIndex("gravity"))).isEqualTo("gravity");

    Realm.deleteRealmFile(getContext(), databaseFileName);
  }

  public void testShouldSaveTextModel() {
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