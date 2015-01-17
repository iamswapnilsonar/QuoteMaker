package com.thuytrinh.quotemaker.model;

import io.realm.RealmObject;

public class QuoteModel extends RealmObject {
  private int backgroundColor;
  private String snapshotFilePath;

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
}