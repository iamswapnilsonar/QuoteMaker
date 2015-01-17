package com.thuytrinh.quotemaker.model;

import io.realm.RealmList;
import io.realm.RealmObject;

public class QuoteModel extends RealmObject {
  private int backgroundColor;
  private String snapshotFilePath;
  private RealmList<TextModel> items;

  public int getBackgroundColor() {
    return backgroundColor;
  }

  public void setBackgroundColor(int backgroundColor) {
    this.backgroundColor = backgroundColor;
  }

  public String getSnapshotFilePath() {
    return snapshotFilePath;
  }

  public void setSnapshotFilePath(String snapshotFilePath) {
    this.snapshotFilePath = snapshotFilePath;
  }

  public RealmList<TextModel> getItems() {
    return items;
  }

  public void setItems(RealmList<TextModel> items) {
    this.items = items;
  }
}